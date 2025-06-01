package kotli.app.feature.presentation

import shared.presentation.state.MutableViewState
import shared.presentation.viewmodel.BaseViewModel

class FeatureViewModel : BaseViewModel() {

    private val _state = FeatureMutableState()
    val state: FeatureState = _state

    private class FeatureMutableState : MutableViewState(), FeatureState
}