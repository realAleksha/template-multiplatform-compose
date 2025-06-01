package feature.passcode.basic.presentation.set

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import feature.passcode.basic.presentation.common.PasscodeKeyboard
import org.jetbrains.compose.resources.stringResource
import shared.presentation.state.ViewStateHandler
import shared.presentation.ui.container.DsFixedTopBarColumn

@Composable
internal fun SetPasscodeScreen(
    onBack: () -> Unit
) {
    val viewModel: SetPasscodeViewModel = koinFeatureViewModel()
    val state = viewModel.state
    val step = state.step ?: return

    ViewStateHandler(
        state = state,
        onEvent = { event ->
            when (event) {
                is SetPasscodeState.OnComplete -> onBack()
            }
        },
        content = {
            DsFixedTopBarColumn(
                onBack = onBack
            ) {
                PasscodeKeyboard(
                    onCodeChange = viewModel::onEnter,
                    title = stringResource(step.titleRes),
                    codeLength = state.passcodeLength,
                    getCode = state::enteredCode,
                    getError = state::error,
                )
            }
        }
    )
}