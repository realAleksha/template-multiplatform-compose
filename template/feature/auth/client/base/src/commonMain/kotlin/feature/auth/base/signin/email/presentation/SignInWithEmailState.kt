package feature.auth.base.signin.email.presentation

import androidx.compose.runtime.Stable
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface SignInWithEmailState : ViewState {

    val email: String
    val canVerify: Boolean

    data class OnVerify(val email: String) : UiEvent
}