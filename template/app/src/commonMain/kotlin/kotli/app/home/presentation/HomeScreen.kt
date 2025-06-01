package kotli.app.home.presentation

import androidx.compose.runtime.Composable
import feature.common.preview.FeaturePreviewProvider
import feature.common.preview.FeaturePreviewScreen
import shared.presentation.viewmodel.provideViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = provideViewModel()
    val state = viewModel.state

    FeaturePreviewScreen(
        title = "Home",
        providers = state.features.filterIsInstance<FeaturePreviewProvider>(),
    )
}