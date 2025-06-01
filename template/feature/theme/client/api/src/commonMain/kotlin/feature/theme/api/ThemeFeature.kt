package feature.theme.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.common.api.Feature

interface ThemeFeature : Feature {

    fun showChangeThemeScreen()

    fun showChangeThemeDialog()

    fun showChangeThemeBottomSheet()

    @Composable
    fun ToggleButton(modifier: Modifier = Modifier)
}