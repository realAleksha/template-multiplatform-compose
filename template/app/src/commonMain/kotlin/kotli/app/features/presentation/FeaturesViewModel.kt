package kotli.app.features.presentation

import feature.common.api.Feature
import feature.common.preview.FeaturePreviewProvider
import shared.presentation.viewmodel.BaseViewModel

class FeaturesViewModel(features: List<Feature>) : BaseViewModel() {

    private val _state = FeaturesMutableState(features.filterIsInstance<FeaturePreviewProvider>())
    val state: FeaturesState = _state

    private class FeaturesMutableState(
        override val previews: List<FeaturePreviewProvider>,
        override val title: String = "Features",
    ) : FeaturesState
}
