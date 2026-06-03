package feature.common.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class ActionFeatureProvider : BaseFeatureProvider() {

    private val actionFlow = MutableSharedFlow<Action>(extraBufferCapacity = Int.MAX_VALUE)

    @Composable
    override fun provideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        LaunchedEffect(context) { actionFlow.collect { action -> onReceiveAction(action, context) } }
        super.provideContent(context, content)
    }

    protected open suspend fun onReceiveAction(action: Action, context: FeatureNavContext) = Unit
    protected fun onSendAction(action: Action) = actionFlow.tryEmit(action)
    interface Action
}