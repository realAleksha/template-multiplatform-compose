package feature.navigation.basic.presentation

import androidx.compose.runtime.Composable
import feature.navigation.api.NavigationType
import feature.navigation.basic.presentation.provider.AdaptiveProvider
import feature.navigation.basic.presentation.provider.BottomProvider
import feature.navigation.basic.presentation.provider.DismissibleProvider
import feature.navigation.basic.presentation.provider.ModalProvider
import feature.navigation.basic.presentation.provider.PermanentProvider
import feature.navigation.basic.presentation.provider.RailProvider

@Composable
internal fun NavigationProvider(
    type: NavigationType,
    state: NavigationState,
    content: @Composable () -> Unit
) {
    when (type) {
        NavigationType.Adaptive -> AdaptiveProvider(state, content)
        NavigationType.Bottom -> BottomProvider(state, content)
        NavigationType.Dismissable -> DismissibleProvider(state, content)
        NavigationType.Modal -> ModalProvider(state, content)
        NavigationType.Permanent -> PermanentProvider(state, content)
        NavigationType.Rail -> RailProvider(state, content)
    }
}