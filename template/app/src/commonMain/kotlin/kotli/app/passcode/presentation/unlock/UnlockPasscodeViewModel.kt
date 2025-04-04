package kotli.app.passcode.presentation.unlock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotli.app.passcode.domain.model.LockState
import kotli.app.passcode.domain.usecase.GetPasscodeLengthUseCase
import kotli.app.passcode.domain.usecase.GetRemainingAttemptsUseCase
import kotli.app.passcode.domain.usecase.UnlockPasscodeUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.viewmodel.BaseViewModel
import template.app.generated.resources.Res
import template.app.generated.resources.passcode_unlock_error

class UnlockPasscodeViewModel(
    getPasscodeLength: GetPasscodeLengthUseCase,
    private val unlockPasscode: UnlockPasscodeUseCase,
    private val getAttempts: GetRemainingAttemptsUseCase,
) : BaseViewModel() {

    private val _state = UnlockPasscodeMutableState(getPasscodeLength.invoke())
    val state: UnlockPasscodeState = _state

    override fun doBind() = withState {
        _state.enteredCode = ""
        _state.loading = false
        _state.error = null
    }

    fun onForgot() {
        _state.forgot = true
    }

    fun onCancelForgot() {
        _state.forgot = false
    }

    fun onUnlock(enteredCode: String) {
        if (_state.passcodeLength == 0) return

        withState {
            _state.enteredCode = enteredCode
            _state.error = null
        }

        if (enteredCode.length != _state.passcodeLength) return

        async("Unlock passcode", force = true) {
            try {
                _state.loading = true
                val lockState = unlockPasscode.invoke(enteredCode)
                if (lockState == LockState.LOCKED) {
                    val attempts = getAttempts.invoke()
                    val error = getString(Res.string.passcode_unlock_error, attempts)
                    withState {
                        _state.enteredCode = ""
                        _state.loading = false
                        _state.error = error
                    }
                }
            } finally {
                _state.loading = false
            }
        }
    }

    private class UnlockPasscodeMutableState(
        override val passcodeLength: Int
    ) : UnlockPasscodeState {
        override var error: String? by mutableStateOf(null)
        override var forgot: Boolean by mutableStateOf(false)
        override var loading: Boolean by mutableStateOf(false)
        override var enteredCode: String by mutableStateOf("")
    }
}
