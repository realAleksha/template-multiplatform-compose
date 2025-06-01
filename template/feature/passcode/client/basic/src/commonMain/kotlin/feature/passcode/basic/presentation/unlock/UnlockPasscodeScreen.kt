package feature.passcode.basic.presentation.unlock

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import feature.passcode.basic.presentation.common.PadTextButton
import feature.passcode.basic.presentation.common.PasscodeKeyboard
import feature.passcode.basic.presentation.forgot.ForgotPasscodeDialog
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.container.DsFixedTopBarColumn
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_unlock_forgot
import template.feature.passcode.client.basic.generated.resources.passcode_unlock_title

@Composable
internal fun UnlockPasscodeScreen() {
    val viewModel: UnlockPasscodeViewModel = koinFeatureViewModel()
    val state = viewModel.state

    ViewStateHandler(state) {
        DsFixedTopBarColumn {
            PasscodeKeyboard(
                title = stringResource(Res.string.passcode_unlock_title),
                onCodeChange = viewModel::onUnlock,
                codeLength = state.passcodeLength,
                getCode = state::enteredCode,
                getError = state::error,
                bottomLeftBlock = {
                    PadTextButton(
                        text = stringResource(Res.string.passcode_unlock_forgot),
                        onClick = viewModel::onForgot
                    )
                }
            )
        }
    }

    ForgotBlock(state, viewModel::onCancelForgot)
}

@Composable
private fun ForgotBlock(
    state: UnlockPasscodeState,
    onHide: () -> Unit
) {
    if (state.forgot) {
        ForgotPasscodeDialog(onHide)
    }
}