package feature.update.sideload.presentation.error

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsAlertDialog
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_error_ok
import template.feature.update.client.sideload.generated.resources.update_error_title

@Composable
internal fun UpdateErrorScreen(
    message: String,
    viewModel: UpdateErrorViewModel,
    onDismiss: () -> Unit
) {
    LaunchedEffect(message) {
        viewModel.state.message = message
    }
    ViewStateHandler(viewModel.state) {
        DsAlertDialog(
            onDismissRequest = onDismiss,
            title = stringResource(Res.string.update_error_title),
            text = viewModel.state.message,
            confirmLabel = stringResource(Res.string.update_error_ok),
            confirmAction = onDismiss
        )
    }
}
