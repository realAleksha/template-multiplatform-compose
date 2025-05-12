package feature.passcode.basic.presentation.provide

import androidx.compose.runtime.Stable
import feature.passcode.basic.domain.model.LockState

@Stable
internal interface PasscodeState {

    val lockState: LockState
}