package feature.passcode.basic.presentation.unlock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.usecase.GetPasscodeLengthUseCase
import feature.passcode.basic.domain.usecase.GetRemainingAttemptsUseCase
import feature.passcode.basic.domain.usecase.UnlockPasscodeUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_unlock_error

internal class UnlockPasscodeViewModel(
    getPasscodeLength: GetPasscodeLengthUseCase,
    private val unlockPasscode: UnlockPasscodeUseCase,
    private val getAttempts: GetRemainingAttemptsUseCase,
) : BaseViewModel() {

    private val _state = UnlockPasscodeMutableState(getPasscodeLength.invoke())
    val state: UnlockPasscodeState = _state

    override fun doBind() = withState {
        _state.enteredCode = ""
        _state.error = null
    }

    fun onForgot() {
        _state.forgot = true
    }

    fun onCancelForgot() {
        _state.forgot = false
    }

    fun onUnlock(code: String) {
        if (_state.passcodeLength == 0) return

        withState {
            _state.enteredCode = code
            _state.error = null
        }

        if (code.length != _state.passcodeLength) return

        async("onUnlock", force = true) {
            _state.tryCatch(
                title = "Unlock passcode",
                onTry = {
                    if (unlockPasscode.invoke(code) == LockState.LOCKED) {
                        withState {
                            uiState = UiState.Blocking
                        }

                        val attempts = getAttempts.invoke()
                        val unlockError = getString(Res.string.passcode_unlock_error, attempts)

                        withState {
                            uiState = UiState.Ready
                            error = unlockError
                            enteredCode = ""
                        }
                    }
                }
            )
        }
    }

    private class UnlockPasscodeMutableState(
        override val passcodeLength: Int
    ) : MutableViewState(), UnlockPasscodeState {
        override var error: String? by mutableStateOf(null)
        override var forgot: Boolean by mutableStateOf(false)
        override var enteredCode: String by mutableStateOf("")
    }
}
