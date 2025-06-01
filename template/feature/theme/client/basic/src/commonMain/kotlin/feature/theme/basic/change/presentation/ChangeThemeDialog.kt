package feature.theme.basic.change.presentation

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import kotlinx.serialization.Serializable
import shared.presentation.ui.component.DsDialogContent

@Serializable
internal object ChangeThemeDialogRoute

@Composable
internal fun ChangeThemeDialog() {
    val viewModel: ChangeThemeViewModel = koinFeatureViewModel()
    DsDialogContent {
        ChangeThemeLayout(
            state = viewModel.state,
            onUseDark = viewModel::onUseDark,
            onUseLight = viewModel::onUseLight,
            onUseSystemDefault = viewModel::onUseSystemDefault,
            onEnableDynamicColors = viewModel::onEnableDynamicColors,
            onDisableDynamicColors = viewModel::onDisableDynamicColors
        )
    }
}