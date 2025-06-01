package kotli.app.features.presentation

import androidx.compose.runtime.Stable
import feature.common.preview.FeaturePreviewProvider

@Stable
interface FeaturesState {

    val title: String
    val previews: List<FeaturePreviewProvider>
}