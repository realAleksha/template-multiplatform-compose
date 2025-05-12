package feature.auth.base.signin.email.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.auth.base.signin.email.domain.usecase.SignInWithEmailUseCase
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel

internal class SignInWithEmailViewModel(
    private val signInWithEmail: SignInWithEmailUseCase
) : BaseViewModel() {

    private val _state = SignInWithEmailMutableState()
    val state: SignInWithEmailState = _state

    fun onChangeEmail(email: String) = withState {
        _state.email = email
        _state.uiState = UiState.Ready
        _state.canVerify = email.isNotEmpty()
    }

    fun onVerify() = async("onVerify") {
        _state.tryCatch(
            title = "Verify email",
            onTry = {
                withState { uiState = UiState.Blocking }
                val email = _state.email
                signInWithEmail.invoke(email)
                withState { uiState = UiState.Ready }
                notify(SignInWithEmailState.OnVerify(email))
            }
        )
    }

    private class SignInWithEmailMutableState : MutableViewState(), SignInWithEmailState {
        override var email: String by mutableStateOf("")
        override var canVerify: Boolean by mutableStateOf(false)
    }
}