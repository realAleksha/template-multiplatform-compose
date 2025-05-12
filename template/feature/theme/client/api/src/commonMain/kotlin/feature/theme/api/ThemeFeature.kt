package feature.theme.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.common.Feature

interface ThemeFeature : Feature {

    fun changeThemeScreen()

    fun changeThemeDialog()

    fun changeThemeBottomSheet()

    @Composable
    fun ToggleButton(modifier: Modifier = Modifier)
}