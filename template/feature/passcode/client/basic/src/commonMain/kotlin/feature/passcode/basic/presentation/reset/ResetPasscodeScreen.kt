package feature.passcode.basic.presentation.reset

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import feature.passcode.basic.presentation.common.PasscodeKeyboard
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.container.DsFixedTopBarColumn
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_reset_title

@Composable
internal fun ResetPasscodeScreen(onBack: () -> Unit) {
    val viewModel: ResetPasscodeViewModel = koinFeatureViewModel()
    val state = viewModel.state

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is ResetPasscodeState.OnComplete -> onBack()
            }
        },
        content = {
            DsFixedTopBarColumn(onBack = onBack) {
                PasscodeKeyboard(
                    getError = state::error,
                    getCode = state::enteredCode,
                    onCodeChange = viewModel::onReset,
                    codeLength = state.passcodeLength,
                    title = stringResource(Res.string.passcode_reset_title),
                )
            }
        }
    )
}