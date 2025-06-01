package feature.passcode.basic.presentation.provide

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.usecase.GetLockStateUseCase
import feature.passcode.basic.domain.usecase.UpdatePasscodeUseCase
import kotlinx.coroutines.flow.collectLatest
import shared.presentation.viewmodel.BaseViewModel

internal class PasscodeViewModel(
    private val getLockState: GetLockStateUseCase,
    private val updatePasscode: UpdatePasscodeUseCase
) : BaseViewModel() {

    private val _state = PasscodeMutableState()
    val state: PasscodeState = _state

    override fun doInit() = init()

    override fun doResume() = init()

    override fun doPause() = async("Pause passcode") {
        updatePasscode.invoke()
    }

    private fun init() = async("Init passcode", true) {
        getLockState.invoke().collectLatest { lockState ->
            _state.lockState = lockState
        }
    }

    private class PasscodeMutableState : PasscodeState {
        override var lockState: LockState by mutableStateOf(LockState.UNDEFINED)
    }
}
