package feature.theme.basic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import feature.common.api.FeatureContext
import feature.common.koin.KoinFeatureProvider
import feature.common.preview.FeatureMethod
import feature.common.preview.FeaturePreviewProvider
import feature.common.preview.method.MethodCallsAction
import feature.common.preview.method.MethodComposable
import feature.theme.api.ThemeFeature
import feature.theme.basic.change.presentation.ChangeThemeBottomSheet
import feature.theme.basic.change.presentation.ChangeThemeBottomSheetRoute
import feature.theme.basic.change.presentation.ChangeThemeDialog
import feature.theme.basic.change.presentation.ChangeThemeDialogRoute
import feature.theme.basic.change.presentation.ChangeThemeRoute
import feature.theme.basic.change.presentation.ChangeThemeScreen
import feature.theme.basic.change.presentation.ChangeThemeViewModel
import feature.theme.basic.provide.data.ThemeRepositoryImpl
import feature.theme.basic.provide.domain.repository.ThemeRepository
import feature.theme.basic.provide.domain.usecase.RestoreThemeUseCase
import feature.theme.basic.provide.domain.usecase.StoreThemeUseCase
import feature.theme.basic.provide.presentation.ThemeProvider
import feature.theme.basic.provide.presentation.ThemeStatefulViewModel
import feature.theme.basic.provide.presentation.ThemeStatelessViewModel
import feature.theme.basic.toggle.presentation.ToggleThemeButton
import feature.theme.basic.toggle.presentation.ToggleThemeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import shared.data.source.settings.SettingsSource
import shared.presentation.theme.DefaultThemeState
import shared.presentation.theme.ThemeConfig
import shared.presentation.theme.ThemeState
import shared.presentation.ui.theme.DsThemes

class BasicThemeProvider(
    private val settingsSource: SettingsSource
) : KoinFeatureProvider(), FeaturePreviewProvider, ThemeFeature {

    override val name: String = "Basic Theme"

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction(
            name = "showChangeThemeScreen()",
            action = ::showChangeThemeScreen
        ),
        MethodCallsAction(
            name = "showChangeThemeDialog()",
            action = ::showChangeThemeDialog
        ),
        MethodCallsAction(
            name = "showChangeThemeBottomSheet()",
            action = ::showChangeThemeBottomSheet
        ),
        MethodComposable(
            name = "ToggleButton()",
            composable = { ToggleButton() }
        )
    )

    override fun showChangeThemeScreen() {
        onSendAction(ChangeThemeScreen)
    }

    override fun showChangeThemeDialog() {
        onSendAction(ChangeThemeDialog)
    }

    override fun showChangeThemeBottomSheet() {
        onSendAction(ChangeThemeBottomSheet)
    }

    @Composable
    override fun ToggleButton(modifier: Modifier) {
        withDI {
            ToggleThemeButton(modifier)
        }
    }

    override fun Module.onProvideDI() {
        single<ThemeState> {
            DefaultThemeState(
                defaultConfig = ThemeConfig(
                    defaultTheme = DsThemes.Light,
                    lightTheme = DsThemes.Light,
                    darkTheme = DsThemes.Dark,
                )
            )
        }
        single<ThemeRepository> {
            ThemeRepositoryImpl(
                settingsSource = settingsSource
            )
        }
        factoryOf(::StoreThemeUseCase)
        factoryOf(::RestoreThemeUseCase)
        viewModelOf(::ToggleThemeViewModel)
        viewModelOf(::ChangeThemeViewModel)
        viewModelOf(::ThemeStatefulViewModel)
        viewModelOf(::ThemeStatelessViewModel)
    }

    @Composable
    override fun onProvideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        withDI {
            ThemeProvider(content)
        }
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureContext) {
        when (action) {
            ChangeThemeScreen -> context.addBackStack(ChangeThemeRoute)
            ChangeThemeDialog -> context.addBackStack(ChangeThemeDialogRoute)
            ChangeThemeBottomSheet -> context.addBackStack(ChangeThemeBottomSheetRoute)
        }
    }

    override fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) =
        builder.run {
            dialog<ChangeThemeBottomSheetRoute> {
                withDI {
                    ChangeThemeBottomSheet(context::popBackStack)
                }
            }
            composable<ChangeThemeRoute> {
                withDI {
                    ChangeThemeScreen(context::popBackStack)
                }
            }
            dialog<ChangeThemeDialogRoute> {
                withDI {
                    ChangeThemeDialog()
                }
            }
        }

    private object ChangeThemeScreen : Action
    private object ChangeThemeDialog : Action
    private object ChangeThemeBottomSheet : Action
}