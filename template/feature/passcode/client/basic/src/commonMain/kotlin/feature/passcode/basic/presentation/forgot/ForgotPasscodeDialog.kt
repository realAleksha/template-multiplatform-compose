package feature.passcode.basic.presentation.forgot

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.component.DsAlertDialog
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_forgot_message
import template.feature.passcode.client.basic.generated.resources.passcode_forgot_no
import template.feature.passcode.client.basic.generated.resources.passcode_forgot_title
import template.feature.passcode.client.basic.generated.resources.passcode_forgot_yes

@Composable
internal fun ForgotPasscodeDialog(onHide: () -> Unit) {
    val viewModel: ForgotPasscodeViewModel = koinFeatureViewModel()
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is ForgotPasscodeState.OnComplete -> onHide()
            }
        },
        content = {
            DsAlertDialog(
                dismissAction = onHide,
                onDismissRequest = onHide,
                confirmAction = viewModel::onConfirm,
                title = stringResource(Res.string.passcode_forgot_title),
                text = stringResource(Res.string.passcode_forgot_message),
                dismissLabel = stringResource(Res.string.passcode_forgot_no),
                confirmLabel = stringResource(Res.string.passcode_forgot_yes),
            )
        }
    )
}