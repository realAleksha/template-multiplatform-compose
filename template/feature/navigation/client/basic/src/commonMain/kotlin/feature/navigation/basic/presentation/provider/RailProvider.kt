package feature.navigation.basic.presentation.provider

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsRailNavigation

@Composable
@NonRestartableComposable
internal fun RailProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        DsRailNavigation(state = state)
        content()
    }
}