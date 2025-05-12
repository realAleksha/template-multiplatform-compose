package feature.theme.basic.change.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import shared.presentation.theme.ThemeConfig
import shared.presentation.theme.ThemeState
import shared.presentation.viewmodel.BaseViewModel

internal class ChangeThemeViewModel(
    private val themeState: ThemeState
) : BaseViewModel() {

    private val _state = ChangeThemeMutableState(themeState)
    val state: ChangeThemeState = _state

    override fun doBind() {
        val config = themeState.dynamicConfig ?: return
        async("Configure dynamic colors") {
            val themes = setOf(config.lightTheme.id, config.darkTheme.id)
            snapshotFlow { themeState.currentConfig }
                .filterNotNull()
                .map { themeConfig -> themeConfig.defaultTheme.id }
                .map { themeId -> themeId in themes }
                .distinctUntilChanged()
                .collectLatest(_state::dynamic::set)
        }
    }

    fun onEnableDynamicColors() {
        val dynamicConfig = themeState.dynamicConfig ?: return
        val currentConfig = themeState.currentConfig ?: return
        themeState.currentConfig = copy(dynamicConfig, currentConfig)
    }

    fun onDisableDynamicColors() {
        val defaultConfig = themeState.defaultConfig
        val currentConfig = themeState.currentConfig ?: return
        themeState.currentConfig = copy(defaultConfig, currentConfig)
    }

    fun onUseSystemDefault() {
        themeState.setAuto()
    }

    fun onUseLight() {
        themeState.setLight()
    }

    fun onUseDark() {
        themeState.setDark()
    }

    private fun copy(to: ThemeConfig, from: ThemeConfig): ThemeConfig {
        return to.copy(
            defaultTheme = if (from.defaultTheme.dark) to.darkTheme else to.lightTheme,
            autoDark = from.autoDark
        )
    }

    private data class ChangeThemeMutableState(
        val themeState: ThemeState
    ) : ChangeThemeState {
        override var dynamic: Boolean? by mutableStateOf(null)
        override var currentConfig: ThemeConfig? by themeState::currentConfig
    }
}
