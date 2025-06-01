package feature.theme.basic.provide.presentation

import androidx.compose.runtime.Composable
import feature.common.koin.koinFeatureViewModel
import shared.presentation.theme.ThemeProvider as SharedThemeProvider

@Composable
internal fun ThemeProvider(content: @Composable () -> Unit) {
    val viewModel: ThemeStatefulViewModel = koinFeatureViewModel()
    SharedThemeProvider(viewModel.state, content)
}