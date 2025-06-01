package feature.theme.basic.toggle.presentation

import androidx.compose.runtime.Stable
import shared.presentation.ui.icon.DsIconModel

@Stable
internal interface ToggleThemeState {

    fun isDark(): Boolean?

    fun getIcon(): DsIconModel?
}