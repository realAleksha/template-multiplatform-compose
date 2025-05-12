package feature.theme.basic.change.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.common.featureViewModel
import kotlinx.serialization.Serializable
import shared.presentation.ui.container.DsBottomSheet

@Serializable
internal object ChangeThemeBottomSheetRoute

@Composable
internal fun ChangeThemeBottomSheet(onBack: () -> Unit) {
    val viewModel: ChangeThemeViewModel = featureViewModel()
    DsBottomSheet(onDismissRequest = onBack) {
        ChangeThemeLayout(
            modifier = Modifier.padding(16.dp),
            state = viewModel.state,
            onUseDark = viewModel::onUseDark,
            onUseLight = viewModel::onUseLight,
            onUseSystemDefault = viewModel::onUseSystemDefault,
            onEnableDynamicColors = viewModel::onEnableDynamicColors,
            onDisableDynamicColors = viewModel::onDisableDynamicColors
        )
    }
}