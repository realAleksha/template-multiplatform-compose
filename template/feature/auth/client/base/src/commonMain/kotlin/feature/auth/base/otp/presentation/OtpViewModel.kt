package feature.auth.base.otp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel

internal class OtpViewModel : BaseViewModel() {

    private val _state = OtpMutableState()
    val state: OtpState = _state

    fun onChangeOtp(otp: String) = withState {
        _state.otp = otp
        _state.uiState = UiState.Ready
        _state.canVerify = otp.isNotEmpty()
    }

    fun onVerify(verify: suspend (otp: String) -> Unit) = async("onVerify") {
        _state.tryCatch(
            title = "Verify OTP",
            onTry = {
                withState { uiState = UiState.Blocking }
                verify.invoke(_state.otp)
                withState { uiState = UiState.Ready }
                notify(OtpState.OnSuccess)
            }
        )
    }

    fun onResend(resend: suspend () -> Unit) = async("onResend") {
        _state.tryCatch(
            title = "Resend OTP",
            onTry = {
                withState { uiState = UiState.Blocking }
                resend.invoke()
                withState { uiState = UiState.Ready }
            }
        )
    }

    private class OtpMutableState : MutableViewState(), OtpState {
        override var canVerify: Boolean by mutableStateOf(false)
        override var otp: String by mutableStateOf("")
    }
}