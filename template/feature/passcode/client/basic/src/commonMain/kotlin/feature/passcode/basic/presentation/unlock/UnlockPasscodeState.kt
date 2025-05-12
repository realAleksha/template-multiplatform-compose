package feature.passcode.basic.presentation.unlock

import androidx.compose.runtime.Stable
import shared.presentation.state.ViewState

@Stable
internal interface UnlockPasscodeState : ViewState {

    val error: String?
    val forgot: Boolean
    val enteredCode: String
    val passcodeLength: Int
}