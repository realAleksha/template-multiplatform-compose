package feature.navigation.basic.presentation.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsModalNavigation

@Composable
@NonRestartableComposable
internal fun ModalProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    DsModalNavigation(
        state = state,
        content = content
    )
}