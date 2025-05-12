package feature.theme.basic.provide.presentation

import androidx.compose.runtime.Composable
import feature.common.featureViewModel
import shared.presentation.theme.ThemeProvider as SharedThemeProvider

@Composable
internal fun ThemeProvider(content: @Composable () -> Unit) {
    val viewModel: ThemeStatefulViewModel = featureViewModel()
    SharedThemeProvider(viewModel.state, content)
}