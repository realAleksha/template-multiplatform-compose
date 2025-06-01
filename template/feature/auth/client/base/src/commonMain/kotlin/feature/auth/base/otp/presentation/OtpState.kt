package feature.auth.base.otp.presentation

import androidx.compose.runtime.Stable
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface OtpState : ViewState {

    val otp: String
    val canVerify: Boolean

    object OnSuccess : UiEvent
}
