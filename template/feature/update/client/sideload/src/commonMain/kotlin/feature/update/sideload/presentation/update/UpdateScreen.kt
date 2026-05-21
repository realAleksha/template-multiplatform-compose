package feature.update.sideload.presentation.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsAlertDialog
import shared.presentation.ui.component.DsDialog
import shared.presentation.ui.component.DsDialogContent
import shared.presentation.ui.component.DsLinearProgressIndicator
import shared.presentation.ui.component.DsText
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_available_confirm
import template.feature.update.client.sideload.generated.resources.update_available_dismiss
import template.feature.update.client.sideload.generated.resources.update_available_message
import template.feature.update.client.sideload.generated.resources.update_available_title
import template.feature.update.client.sideload.generated.resources.update_downloading_title
import template.feature.update.client.sideload.generated.resources.update_progress

@Composable
internal fun UpdateScreen(
    url: String,
    viewModel: UpdateViewModel,
    onDismiss: () -> Unit
) {
    val state = viewModel.state

    ViewStateHandler(state) {
        DsAlertDialog(
            onDismissRequest = onDismiss,
            title = stringResource(Res.string.update_available_title),
            text = stringResource(Res.string.update_available_message),
            confirmLabel = stringResource(Res.string.update_available_confirm),
            confirmAction = { viewModel.onDownload(url) },
            dismissLabel = stringResource(Res.string.update_available_dismiss),
            dismissAction = onDismiss
        )

        if (state.progress > 0f && state.progress < 1f) {
            DsDialog(
                onDismissRequest = {}
            ) {
                DsDialogContent {
                    Column {
                        DsText(text = stringResource(Res.string.update_downloading_title))
                        DsText(
                            text = stringResource(Res.string.update_progress, (state.progress * 100).toInt()),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        DsLinearProgressIndicator(
                            progress = state.progress,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
