package feature.navigation.basic.presentation.provider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import feature.navigation.basic.presentation.NavigationState
import shared.presentation.ui.container.DsBottomNavigation

@Composable
@NonRestartableComposable
internal fun BottomProvider(
    state: NavigationState,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) { content() }
        DsBottomNavigation(
            state = state,
            modifier = Modifier.wrapContentHeight()
        )
    }
}