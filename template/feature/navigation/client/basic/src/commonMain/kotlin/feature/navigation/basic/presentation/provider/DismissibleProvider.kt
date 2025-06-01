package feature.navigation.basic.presentation.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsDismissibleNavigation

@Composable
@NonRestartableComposable
internal fun DismissibleProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    DsDismissibleNavigation(
        state = state,
        content = content
    )
}