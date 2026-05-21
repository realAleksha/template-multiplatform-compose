package feature.update.sideload.presentation.install

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import feature.update.sideload.SideloadUpdateState
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsCircularProgressIndicator
import shared.presentation.ui.component.DsDialogContent

@Composable
internal fun UpdateInstallScreen(
    filePath: String,
    viewModel: UpdateInstallViewModel,
    onResult: (SideloadUpdateState) -> Unit
) {
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is UpdateInstallState.OnState -> onResult(event.state)
            }
        }
    ) {
        LaunchedEffect(filePath) {
            viewModel.onInstall(filePath)
        }

        DsDialogContent {
            DsCircularProgressIndicator()
        }
    }
}
