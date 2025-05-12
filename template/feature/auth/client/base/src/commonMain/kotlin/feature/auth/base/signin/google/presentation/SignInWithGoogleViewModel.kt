package feature.auth.base.signin.google.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import feature.auth.base.signin.google.presentation.flow.GoogleFlow
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import shared.presentation.viewmodel.BaseViewModel

internal class SignInWithGoogleViewModel(
    private val flowProvider: GoogleFlowProvider
) : BaseViewModel() {

    private lateinit var flow: GoogleFlow

    @Composable
    override fun DoBind() {
        flow = flowProvider.provide(viewModelScope)
    }

    fun onSignIn() = flow.start()
}