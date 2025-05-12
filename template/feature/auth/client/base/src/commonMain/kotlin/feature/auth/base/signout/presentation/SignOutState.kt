package feature.auth.base.signout.presentation

import androidx.compose.runtime.Stable
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface SignOutState : ViewState {

    object OnSuccess : UiEvent
}