package feature.theme.basic.toggle.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.common.koin.koinFeatureViewModel
import shared.presentation.ui.component.DsActionButton

@Composable
internal fun ToggleThemeButton(modifier: Modifier = Modifier) {
    val viewModel: ToggleThemeViewModel = koinFeatureViewModel()
    val state = viewModel.state

    DsActionButton(
        modifier = modifier,
        icon = state.getIcon(),
        onClick = viewModel::onToggle,
    )
}