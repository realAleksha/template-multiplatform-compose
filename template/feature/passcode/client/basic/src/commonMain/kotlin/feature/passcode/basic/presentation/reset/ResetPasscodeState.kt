package feature.passcode.basic.presentation.reset

import androidx.compose.runtime.Stable
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface ResetPasscodeState : ViewState {

    val error: String?
    val enteredCode: String
    val passcodeLength: Int

    object OnComplete : UiEvent
}