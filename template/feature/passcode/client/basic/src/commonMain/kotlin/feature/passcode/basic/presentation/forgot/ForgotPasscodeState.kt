package feature.passcode.basic.presentation.forgot

import androidx.compose.runtime.Stable
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface ForgotPasscodeState : ViewState {

    object OnComplete : UiEvent
}