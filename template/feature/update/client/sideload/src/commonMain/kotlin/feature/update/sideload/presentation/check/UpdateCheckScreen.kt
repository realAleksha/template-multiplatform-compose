package feature.update.sideload.presentation.check

import androidx.compose.runtime.Composable
import feature.update.sideload.SideloadUpdateState
import shared.presentation.state.ViewStateHandler

@Composable
internal fun UpdateCheckScreen(
    viewModel: UpdateCheckViewModel,
    onResult: (SideloadUpdateState) -> Unit
) {
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is UpdateCheckState.OnState -> {
                    onResult(event.state)
                }
            }
        }
    ) {

    }
}
