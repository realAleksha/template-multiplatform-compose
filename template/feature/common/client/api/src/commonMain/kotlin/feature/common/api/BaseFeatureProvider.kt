package feature.common.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseFeatureProvider : FeatureProvider {

    private val actionFlow = MutableSharedFlow<Action>(extraBufferCapacity = Int.MAX_VALUE)

    @Composable
    override fun provideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        LaunchedEffect(context) { actionFlow.collect { action -> onReceiveAction(action, context) } }
        onProvideContent(context, content)
    }

    @Composable
    abstract fun onProvideContent(context: FeatureContext, content: @Composable () -> Unit)

    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) {
        onProvideNavigation(context, builder)
    }

    open fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) = Unit

    protected open suspend fun onReceiveAction(action: Action, context: FeatureContext) = Unit
    protected fun onSendAction(action: Action) = actionFlow.tryEmit(action)
    interface Action
}