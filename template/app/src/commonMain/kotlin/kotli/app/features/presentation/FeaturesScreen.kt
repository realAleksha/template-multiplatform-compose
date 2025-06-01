package kotli.app.features.presentation

import androidx.compose.runtime.Composable
import feature.common.preview.FeaturePreviewScreen
import shared.presentation.viewmodel.provideViewModel

@Composable
fun FeaturesScreen() {
    val viewModel: FeaturesViewModel = provideViewModel()
    val state = viewModel.state

    FeaturePreviewScreen(
        title = state.title,
        previewProviders = state.previews,
    )
}