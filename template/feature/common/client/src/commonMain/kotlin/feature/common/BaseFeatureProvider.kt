package feature.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.compose.KoinIsolatedContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import shared.presentation.viewmodel.BaseViewModel

@Composable
inline fun <reified T : BaseViewModel> featureViewModel(key: String? = null): T {
    val viewModel = koinViewModel<T>(key = key)
    viewModel.bind()
    return viewModel
}

abstract class BaseFeatureProvider : FeatureProvider {

    protected val koinApp = koinApplication(createEagerInstances = false) { modules(module { provideDI() }) }
    private val actionFlow = MutableSharedFlow<Action>()

    // DI
    @Composable
    protected fun withDI(content: @Composable () -> Unit) = KoinIsolatedContext(koinApp, content)
    protected open fun Module.provideDI() = Unit

    // CONTENT
    @Composable
    final override fun provideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        LaunchedEffect(context) { actionFlow.collect { action -> onReceiveAction(action, context) } }
        DisposableEffect(Unit) { onDispose(koinApp::close) }
        provideUI(context, content)
    }

    @Composable
    protected open fun provideUI(context: FeatureContext, content: @Composable () -> Unit) = content()

    // NAVIGATION
    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) = Unit

    // ACTIONS
    protected open suspend fun onReceiveAction(action: Action, context: FeatureContext) = Unit
    protected fun onSendAction(action: Action) = actionFlow.tryEmit(action)
    interface Action
}