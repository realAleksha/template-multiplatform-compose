package feature.passcode.basic.presentation.reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.usecase.CheckPasscodeUseCase
import feature.passcode.basic.domain.usecase.GetPasscodeLengthUseCase
import feature.passcode.basic.domain.usecase.GetRemainingAttemptsUseCase
import feature.passcode.basic.domain.usecase.ResetPasscodeUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_unlock_error

internal class ResetPasscodeViewModel(
    getPasscodeLength: GetPasscodeLengthUseCase,
    private val resetPasscode: ResetPasscodeUseCase,
    private val checkPasscode: CheckPasscodeUseCase,
    private val getAttempts: GetRemainingAttemptsUseCase,
) : BaseViewModel() {

    private val _state = ResetPasscodeMutableState(getPasscodeLength.invoke())
    val state: ResetPasscodeState = _state

    fun onReset(code: String) {
        if (_state.passcodeLength == 0) return

        withState {
            _state.enteredCode = code
            _state.error = null
        }

        if (code.length != _state.passcodeLength) return

        async("onReset", force = true) {
            _state.tryCatch(
                title = "Reset passcode",
                onTry = {
                    withState { uiState = UiState.Blocking }
                    if (checkPasscode.invoke(code) == LockState.UNLOCKED) {
                        resetPasscode.invoke()
                        _state.notify(ResetPasscodeState.OnComplete)
                        withState { uiState = UiState.Ready }
                    } else {
                        val attempts = getAttempts.invoke()
                        val unlockError = getString(Res.string.passcode_unlock_error, attempts)
                        withState {
                            enteredCode = ""
                            error = unlockError
                            uiState = UiState.Ready
                        }
                    }
                }
            )
        }
    }

    private class ResetPasscodeMutableState(
        override val passcodeLength: Int
    ) : MutableViewState(), ResetPasscodeState {
        override var error: String? by mutableStateOf(null)
        override var enteredCode: String by mutableStateOf("")
    }
}
