package feature.update.sideload

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.koin.KoinActionFeatureProvider
import feature.common.koin.koinFeatureViewModel
import feature.update.api.UpdateFeature
import feature.update.sideload.data.repository.UpdateRepositoryImpl
import feature.update.sideload.domain.repository.UpdateRepository
import feature.update.sideload.domain.usecase.DownloadUpdateUseCase
import feature.update.sideload.domain.usecase.GetUpdatePathUseCase
import feature.update.sideload.domain.usecase.InstallUpdateUseCase
import feature.update.sideload.domain.usecase.SetUpdatePathUseCase
import feature.update.sideload.presentation.check.UpdateCheckRoute
import feature.update.sideload.presentation.check.UpdateCheckScreen
import feature.update.sideload.presentation.check.UpdateCheckViewModel
import feature.update.sideload.presentation.error.UpdateErrorRoute
import feature.update.sideload.presentation.error.UpdateErrorScreen
import feature.update.sideload.presentation.error.UpdateErrorViewModel
import feature.update.sideload.presentation.force_update.ForceUpdateRoute
import feature.update.sideload.presentation.force_update.ForceUpdateScreen
import feature.update.sideload.presentation.force_update.ForceUpdateViewModel
import feature.update.sideload.presentation.install.UpdateInstallRoute
import feature.update.sideload.presentation.install.UpdateInstallScreen
import feature.update.sideload.presentation.install.UpdateInstallViewModel
import feature.update.sideload.presentation.update.UpdateRoute
import feature.update.sideload.presentation.update.UpdateScreen
import feature.update.sideload.presentation.update.UpdateViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import shared.data.http.HttpSource
import shared.data.settings.SettingsSource
import kotlin.reflect.KClass

class SideloadUpdateProvider(
    private val httpSource: HttpSource,
    private val settingsSource: SettingsSource,
    private val resolver: SideloadUpdateStateResolver
) : KoinActionFeatureProvider(), FeaturePreview, UpdateFeature {

    override val name: String = "Sideload Update"

    override val type: KClass<out Feature> = UpdateFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction(
            "checkForUpdates()",
            action = ::checkForUpdates
        ),
        MethodCallsAction(
            "SideloadUpdateState.Optional",
            action = {
                onSendAction(StateAction(SideloadUpdateState.Optional("https://some.url")))
            }
        ),
        MethodCallsAction(
            "SideloadUpdateState.Force",
            action = {
                onSendAction(StateAction(SideloadUpdateState.Force("https://some.url")))
            }
        ),
        MethodCallsAction(
            "SideloadUpdateState.ReadyToInstall",
            action = {
                onSendAction(StateAction(SideloadUpdateState.ReadyToInstall("update.apk")))
            }
        )
    )

    override fun Module.onProvideDI() {
        singleOf(::httpSource)
        singleOf(::settingsSource)
        singleOf(::UpdateRepositoryImpl).bind<UpdateRepository>()
        single<SideloadUpdateStateResolver> { PersistentSideloadUpdateStateResolver(get(), resolver) }

        factoryOf(::SetUpdatePathUseCase)
        factoryOf(::InstallUpdateUseCase)
        factoryOf(::GetUpdatePathUseCase)
        factoryOf(::DownloadUpdateUseCase)

        viewModelOf(::UpdateViewModel)
        viewModelOf(::ForceUpdateViewModel)
        viewModelOf(::UpdateCheckViewModel)
        viewModelOf(::UpdateInstallViewModel)
        viewModelOf(::UpdateErrorViewModel)
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureNavContext) {
        when (action) {
            CheckForUpdates -> context.pushDestination(UpdateCheckRoute)
            is StateAction -> handleState(action.state, context)
        }
    }

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        withDI {
            content()
            LaunchedEffect(Unit) {
                val resolver: SideloadUpdateStateResolver = koinApp.koin.get()
                val state = resolver.resolve()
                if (state !is SideloadUpdateState.Error) {
                    handleState(state, context)
                }
            }
        }
    }

    override fun onProvideNavigation(context: FeatureNavContext, builder: NavGraphBuilder) {
        builder.run {
            dialog<ForceUpdateRoute> { backStackEntry ->
                withDI {
                    val route: ForceUpdateRoute = backStackEntry.toRoute()
                    val viewModel: ForceUpdateViewModel = koinFeatureViewModel()
                    ForceUpdateScreen(route.url, viewModel) { state -> handleState(state, context) }
                }
            }
            dialog<UpdateRoute> { backStackEntry ->
                withDI {
                    val route: UpdateRoute = backStackEntry.toRoute()
                    val viewModel: UpdateViewModel = koinFeatureViewModel()
                    UpdateScreen(route.url, viewModel, context::popDestination)
                }
            }
            dialog<UpdateCheckRoute> {
                withDI {
                    val viewModel: UpdateCheckViewModel = koinFeatureViewModel()
                    UpdateCheckScreen(viewModel) { state -> handleState(state, context) }
                }
            }
            dialog<UpdateInstallRoute> { backStackEntry ->
                withDI {
                    val route: UpdateInstallRoute = backStackEntry.toRoute()
                    val viewModel: UpdateInstallViewModel = koinFeatureViewModel()
                    UpdateInstallScreen(route.filePath, viewModel) { state -> handleState(state, context) }
                }
            }
            dialog<UpdateErrorRoute> { backStackEntry ->
                withDI {
                    val route: UpdateErrorRoute = backStackEntry.toRoute()
                    val viewModel: UpdateErrorViewModel = koinFeatureViewModel()
                    UpdateErrorScreen(route.message, viewModel, context::popDestination)
                }
            }
        }
    }

    override fun checkForUpdates() {
        onSendAction(CheckForUpdates)
    }

    private fun handleState(state: SideloadUpdateState, context: FeatureNavContext) {
        when (state) {
            is SideloadUpdateState.Force -> context.pushDestination(ForceUpdateRoute(state.url))
            is SideloadUpdateState.Optional -> context.pushDestination(UpdateRoute(state.url))
            is SideloadUpdateState.ReadyToInstall -> context.pushDestination(UpdateInstallRoute(state.filePath))
            is SideloadUpdateState.Error -> context.replaceDestination(UpdateErrorRoute(state.message))
            is SideloadUpdateState.None -> Unit
        }
    }

    private object CheckForUpdates : Action
    private data class StateAction(val state: SideloadUpdateState) : Action
    override fun isAvailable(): Boolean = isSupported()
}

internal expect fun isSupported(): Boolean
