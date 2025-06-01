package feature.auth.base.signout.presentation

import feature.auth.base.signout.domain.usecase.SignOutUseCase
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel

internal class SignOutViewModel(
    private val signOut: SignOutUseCase
) : BaseViewModel() {

    private val _state = SignOutMutableState()
    val state: SignOutState = _state

    fun onConfirm() = async("onConfirm") {
        _state.tryCatch(
            "Sign out",
            onTry = {
                withState { uiState = UiState.Blocking }
                signOut.invoke()
                _state.notify(SignOutState.OnSuccess)
                withState { uiState = UiState.Ready }
            }
        )
    }

    private class SignOutMutableState : MutableViewState(), SignOutState
}