package feature.passcode.basic.presentation.forgot

import feature.passcode.basic.domain.usecase.ForgotPasscodeUseCase
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel

internal class ForgotPasscodeViewModel(
    private val forgotPasscode: ForgotPasscodeUseCase
) : BaseViewModel() {

    private val _state = ForgotPasscodeMutableState()
    val state: ForgotPasscodeState = _state

    fun onConfirm() = async("onConfirm") {
        _state.tryCatch(
            title = "Reset passcode",
            onTry = {
                try {
                    withState { uiState = UiState.Blocking }
                    forgotPasscode.invoke()
                } finally {
                    withState { uiState = UiState.Ready }
                    notify(ForgotPasscodeState.OnComplete)
                }
            },
            onCatch = {}
        )
    }

    private class ForgotPasscodeMutableState : MutableViewState(), ForgotPasscodeState
}
