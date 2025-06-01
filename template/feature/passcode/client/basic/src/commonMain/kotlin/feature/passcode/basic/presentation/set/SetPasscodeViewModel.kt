package feature.passcode.basic.presentation.set

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.usecase.CheckPasscodeUseCase
import feature.passcode.basic.domain.usecase.GetPasscodeLengthUseCase
import feature.passcode.basic.domain.usecase.GetRemainingAttemptsUseCase
import feature.passcode.basic.domain.usecase.IsPasscodeSetUseCase
import feature.passcode.basic.domain.usecase.SetPasscodeUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.notify
import shared.presentation.viewmodel.BaseViewModel
import template.feature.passcode.client.basic.generated.resources.Res
import template.feature.passcode.client.basic.generated.resources.passcode_set_confirm_new_error
import template.feature.passcode.client.basic.generated.resources.passcode_unlock_error

internal class SetPasscodeViewModel(
    getPasscodeLength: GetPasscodeLengthUseCase,
    private val setPasscode: SetPasscodeUseCase,
    private val isPasscodeSet: IsPasscodeSetUseCase,
    private val checkPasscode: CheckPasscodeUseCase,
    private val getAttempts: GetRemainingAttemptsUseCase,
) : BaseViewModel() {

    private val _state = SetPasscodeMutableState(getPasscodeLength.invoke())
    val state: SetPasscodeState = _state

    override fun doBind() = async("Init state") {
        val step = if (isPasscodeSet.invoke()) {
            SetPasscodeStep.UnlockExisting()
        } else {
            SetPasscodeStep.EnterNew()
        }
        withState {
            _state.enteredCode = ""
            _state.error = null
            _state.step = step
        }
    }

    fun onEnter(code: String) {
        if (_state.passcodeLength == 0) return

        withState {
            _state.enteredCode = code
            _state.error = null
        }

        if (code.length != _state.passcodeLength) return

        async("Check passcode", force = true) {
            when (val step = _state.step) {
                is SetPasscodeStep.ConfirmNew -> onConfirmNew(step.code, code)
                is SetPasscodeStep.UnlockExisting -> onUnlockExisting(code)
                is SetPasscodeStep.EnterNew -> onEnterNew(code)
                else -> Unit
            }
        }
    }

    private suspend fun onUnlockExisting(enteredCode: String) {
        if (checkPasscode.invoke(enteredCode) == LockState.LOCKED) {
            val attempts = getAttempts.invoke()
            val error = getString(Res.string.passcode_unlock_error, attempts)
            withState {
                _state.enteredCode = ""
                _state.error = error
            }
        } else {
            withState {
                _state.step = SetPasscodeStep.EnterNew()
                _state.enteredCode = ""
            }
        }
    }

    private fun onEnterNew(enteredCode: String) {
        withState {
            _state.step = SetPasscodeStep.ConfirmNew(code = enteredCode)
            _state.enteredCode = ""
        }
    }

    private suspend fun onConfirmNew(
        expectedCode: String,
        enteredCode: String
    ) {
        if (enteredCode != expectedCode) {
            val error = getString(Res.string.passcode_set_confirm_new_error)
            withState {
                _state.step = SetPasscodeStep.EnterNew()
                _state.error = error
                _state.enteredCode = ""
            }
        } else {
            setPasscode.invoke(enteredCode)
            _state.notify(SetPasscodeState.OnComplete)
        }
    }

    private class SetPasscodeMutableState(
        override val passcodeLength: Int
    ) : MutableViewState(), SetPasscodeState {
        override var error: String? by mutableStateOf(null)
        override var enteredCode: String by mutableStateOf("")
        override var step: SetPasscodeStep? by mutableStateOf(null)
    }
}
