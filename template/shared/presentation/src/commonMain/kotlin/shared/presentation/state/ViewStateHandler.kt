package shared.presentation.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import shared.presentation.ui.component.DsCard
import shared.presentation.ui.component.DsCircularProgressIndicator
import shared.presentation.ui.component.DsDialog
import shared.presentation.ui.component.DsErrorDialog

/**
 * A composable function that handles the UI state and events from a [ViewState].
 * 
 * This function renders the main content and overlays appropriate UI elements based on
 * the current UI state (loading, blocking, error). It also collects UI events from the
 * state and forwards them to the provided handler.
 * 
 * @param state The [ViewState] to handle.
 * @param onEvent Handler for UI events emitted by the state.
 * @param blockingSlot Custom composable to show when in [UiState.Blocking] state.
 * @param loadingSlot Custom composable to show when in [UiState.Loading] state.
 * @param errorSlot Custom composable to show when in [UiState.Error] state.
 * @param content The main content to display.
 */
@Composable
fun ViewStateHandler(
    state: ViewState,
    onEvent: suspend (UiEvent) -> Unit = {},
    blockingSlot: @Composable (UiState.Blocking) -> Unit = { ViewStateBlocking() },
    loadingSlot: @Composable (UiState.Loading) -> Unit = {},
    errorSlot: @Composable (UiState.Error) -> Unit = { ViewStateError(it) },
    content: @Composable () -> Unit
) {
    LaunchedEffect(state) {
        state.uiEvent.collect(onEvent)
    }

    content()

    LoadingIndicator(
        state = state,
        blockingSlot = blockingSlot,
        loadingSlot = loadingSlot,
        errorSlot = errorSlot,
    )
}

/**
 * A composable function that displays appropriate UI elements based on the current UI state.
 * 
 * This function is marked as [NonRestartableComposable] to optimize recomposition.
 * 
 * @param state The [ViewState] to handle.
 * @param blockingSlot Custom composable to show when in [UiState.Blocking] state.
 * @param loadingSlot Custom composable to show when in [UiState.Loading] state.
 * @param errorSlot Custom composable to show when in [UiState.Error] state.
 */
@Composable
@NonRestartableComposable
fun LoadingIndicator(
    state: ViewState,
    blockingSlot: @Composable (UiState.Blocking) -> Unit = { ViewStateBlocking() },
    loadingSlot: @Composable (UiState.Loading) -> Unit = { ViewStateLoading() },
    errorSlot: @Composable (UiState.Error) -> Unit = { ViewStateError(it) },
) {
    val uiState = state.uiState

    when (uiState) {
        is UiState.Blocking -> blockingSlot(uiState)
        is UiState.Loading -> loadingSlot(uiState)
        is UiState.Error -> errorSlot(uiState)
        else -> Unit
    }
}

/**
 * Default composable implementation for the [UiState.Blocking] state.
 * 
 * Displays a non-dismissible dialog with a centered circular progress indicator.
 */
@Composable
fun ViewStateBlocking() {
    DsDialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DsCard {
                DsCircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

/**
 * Default composable implementation for the [UiState.Loading] state.
 * 
 * This is an empty implementation that can be overridden by custom implementations.
 */
@Composable
fun ViewStateLoading() {
}

/**
 * Default composable implementation for the [UiState.Error] state.
 * 
 * Displays an error dialog with the error title and details.
 * 
 * @param uiState The error state containing error information.
 */
@Composable
fun ViewStateError(uiState: UiState.Error) {
    DsErrorDialog(
        onClose = uiState.onExit,
        title = uiState.title,
        th = uiState.th
    )
}
