package feature.auth.base.userflow.presentation.basic

import androidx.compose.runtime.Stable
import feature.auth.api.AuthUser
import shared.presentation.state.ViewState

@Stable
internal interface BasicAuthState : ViewState {

    val authUser: AuthUser?
}