package feature.navigation.basic.presentation.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsRailNavigation

@Composable
@NonRestartableComposable
internal fun RailProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    DsRailNavigation(
        state = state,
        content = content
    )
}