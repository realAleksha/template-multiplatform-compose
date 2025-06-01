package feature.theme.basic.change.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import shared.presentation.ui.component.DsRadioButton
import shared.presentation.ui.component.DsText
import shared.presentation.ui.component.DsTextHeader
import template.feature.theme.client.basic.generated.resources.Res
import template.feature.theme.client.basic.generated.resources.theme_change_dark_mode
import template.feature.theme.client.basic.generated.resources.theme_change_dark_mode_off
import template.feature.theme.client.basic.generated.resources.theme_change_dark_mode_on
import template.feature.theme.client.basic.generated.resources.theme_change_dark_mode_system
import template.feature.theme.client.basic.generated.resources.theme_change_dynamic_color
import template.feature.theme.client.basic.generated.resources.theme_change_dynamic_color_off
import template.feature.theme.client.basic.generated.resources.theme_change_dynamic_color_on

@Composable
internal fun ChangeThemeLayout(
    modifier: Modifier = Modifier,
    state: ChangeThemeState,
    onUseDark: () -> Unit,
    onUseLight: () -> Unit,
    onUseSystemDefault: () -> Unit,
    onEnableDynamicColors: () -> Unit,
    onDisableDynamicColors: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        DynamicColorBlock(
            state = state,
            onEnableDynamicColors = onEnableDynamicColors,
            onDisableDynamicColors = onDisableDynamicColors
        )
        DarkModePreferenceBlock(
            state = state,
            onUseDark = onUseDark,
            onUseLight = onUseLight,
            onUseSystemDefault = onUseSystemDefault
        )
    }
}

@Composable
private fun DynamicColorBlock(
    state: ChangeThemeState,
    onEnableDynamicColors: () -> Unit,
    onDisableDynamicColors: () -> Unit,
) {
    val dynamic = state.dynamic ?: return
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HeaderBlock(stringResource(Res.string.theme_change_dynamic_color))
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dynamic_color_on),
            selected = dynamic,
            onClick = onEnableDynamicColors
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dynamic_color_off),
            selected = !dynamic,
            onClick = onDisableDynamicColors
        )
    }
}

@Composable
private fun DarkModePreferenceBlock(
    state: ChangeThemeState,
    onUseDark: () -> Unit,
    onUseLight: () -> Unit,
    onUseSystemDefault: () -> Unit
) {
    val config = state.currentConfig ?: return
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HeaderBlock(stringResource(Res.string.theme_change_dark_mode))
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_system),
            selected = config.autoDark,
            onClick = onUseSystemDefault
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_off),
            selected = !config.autoDark && !config.defaultTheme.dark,
            onClick = onUseLight
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_on),
            selected = !config.autoDark && config.defaultTheme.dark,
            onClick = onUseDark
        )
    }
}

@Composable
private fun HeaderBlock(title: String) {
    DsTextHeader(
        modifier = Modifier.padding(vertical = 16.dp),
        text = title,
    )
}

@Composable
private fun ToggleBlock(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DsRadioButton(
            selected = selected,
            onClick = onClick
        )
        DsText(
            text = label
        )
    }
}