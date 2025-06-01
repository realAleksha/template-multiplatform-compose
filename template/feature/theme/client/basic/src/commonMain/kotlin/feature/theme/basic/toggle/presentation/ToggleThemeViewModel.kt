package feature.theme.basic.toggle.presentation

import shared.presentation.ui.icon.DsIconModel
import shared.presentation.ui.icon.DsIcons
import shared.presentation.theme.ThemeState
import shared.presentation.viewmodel.BaseViewModel

internal class ToggleThemeViewModel(
    private val themeState: ThemeState
) : BaseViewModel() {

    private val _state = ToggleThemeMutableState(themeState)
    val state: ToggleThemeState = _state

    fun onToggle() {
        val dark = _state.isDark() ?: return

        if (dark) {
            themeState.setLight()
        } else {
            themeState.setDark()
        }
    }

    private data class ToggleThemeMutableState(
        private val themeState: ThemeState
    ) : ToggleThemeState {

        override fun isDark(): Boolean? {
            return themeState.currentTheme?.dark
        }

        override fun getIcon(): DsIconModel? {
            val theme = themeState.currentTheme

            return when {
                theme == null -> null
                theme.dark -> DsIcons.lightMode
                else -> DsIcons.darkMode
            }
        }
    }
}
