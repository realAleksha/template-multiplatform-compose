package feature.passcode.basic.presentation.provide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.common.koin.koinFeatureViewModel
import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.presentation.unlock.UnlockPasscodeScreen
import shared.presentation.ui.theme.DsTheme

@Composable
internal fun PasscodeProvider(content: @Composable () -> Unit) {
    val viewModel: PasscodeViewModel = koinFeatureViewModel()
    val state = viewModel.state

    when (val lockState = state.lockState) {
        LockState.UNDEFINED -> UndefinedState()
        else -> {
            content()
            if (lockState == LockState.LOCKED) {
                UnlockPasscodeScreen()
            }
        }
    }
}

@Composable
private fun UndefinedState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DsTheme.current.surface)
    )
}