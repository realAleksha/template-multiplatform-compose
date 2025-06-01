package kotli.app.home.presentation

import feature.common.api.Feature
import shared.presentation.viewmodel.BaseViewModel

class HomeViewModel(features: List<Feature>) : BaseViewModel() {

    private val _state = HomeMutableState(features)
    val state: HomeState = _state

    private class HomeMutableState(
        override val features: List<Feature> = emptyList()
    ) : HomeState
}
