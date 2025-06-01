package feature.theme.basic.provide.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import feature.common.koin.koinFeatureViewModel
import shared.presentation.theme.ThemeProvider as SharedThemeProvider

@Composable
internal fun ThemeProvider(content: @Composable () -> Unit) {
    val viewModel: ThemeViewModel = koinFeatureViewModel()
    LaunchedEffect(Unit) { viewModel.onBind() }
    SharedThemeProvider(viewModel.state, content)
}