package feature.navigation.basic.presentation.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsAdaptiveLayout
import shared.presentation.ui.container.LayoutSize

@Composable
@NonRestartableComposable
internal fun AdaptiveProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    DsAdaptiveLayout { size ->
        when {
            size <= LayoutSize.Compact -> BottomProvider(state, content)
            size < LayoutSize.Large -> RailProvider(state, content)
            else -> PermanentProvider(state, content)
        }
    }
}