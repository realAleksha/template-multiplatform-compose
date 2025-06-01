package feature.theme.basic.provide.presentation

import shared.presentation.theme.ThemeState
import shared.presentation.viewmodel.BaseViewModel

internal class ThemeStatelessViewModel(val state: ThemeState) : BaseViewModel() {

    init {
        state.currentConfig = state.defaultConfig
    }
}