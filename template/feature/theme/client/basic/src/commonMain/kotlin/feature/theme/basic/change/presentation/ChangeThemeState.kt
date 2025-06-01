package feature.theme.basic.change.presentation

import androidx.compose.runtime.Stable
import shared.presentation.theme.ThemeConfig

@Stable
internal interface ChangeThemeState {

    val currentConfig: ThemeConfig?
    val dynamic: Boolean?

}