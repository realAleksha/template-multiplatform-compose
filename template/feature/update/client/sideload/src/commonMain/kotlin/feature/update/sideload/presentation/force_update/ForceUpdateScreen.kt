package feature.update.sideload.presentation.force_update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.update.sideload.SideloadUpdateState
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsDialogContent
import shared.presentation.ui.component.DsElevatedButton
import shared.presentation.ui.component.DsLinearProgressIndicator
import shared.presentation.ui.component.DsText
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_downloading_progress
import template.feature.update.client.sideload.generated.resources.update_force_action
import template.feature.update.client.sideload.generated.resources.update_force_message
import template.feature.update.client.sideload.generated.resources.update_force_title

@Composable
internal fun ForceUpdateScreen(
    url: String,
    viewModel: ForceUpdateViewModel,
    onResult: (SideloadUpdateState) -> Unit
) {
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is ForceUpdateState.OnState -> onResult(event.state)
            }
        }
    ) {
        DsDialogContent {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DsText(text = stringResource(Res.string.update_force_title))
                Spacer(modifier = Modifier.height(8.dp))
                DsText(text = stringResource(Res.string.update_force_message))
                Spacer(modifier = Modifier.height(16.dp))

                if (state.isDownloading) {
                    DsText(
                        text = stringResource(
                            Res.string.update_downloading_progress,
                            (state.progress * 100).toInt()
                        )
                    )
                    DsLinearProgressIndicator(
                        progress = state.progress,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } else {
                    DsElevatedButton(
                        onClick = { viewModel.onDownload(url) },
                        text = stringResource(Res.string.update_force_action)
                    )
                }
            }
        }
    }
}
