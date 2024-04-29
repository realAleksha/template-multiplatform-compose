package app.userflow.theme.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import shared.core.provideViewModel
import shared.core.theme.ThemeContext
import shared.design.component.basic.Spacer8
import shared.design.container.FixedTopBarColumnLayout
import template.composeapp.generated.resources.Res
import template.composeapp.generated.resources.theme_change_dark_mode
import template.composeapp.generated.resources.theme_change_dark_mode_off
import template.composeapp.generated.resources.theme_change_dark_mode_on
import template.composeapp.generated.resources.theme_change_dark_mode_system
import template.composeapp.generated.resources.theme_change_dynamic_color
import template.composeapp.generated.resources.theme_change_dynamic_color_off
import template.composeapp.generated.resources.theme_change_dynamic_color_on
import template.composeapp.generated.resources.theme_change_title

@Composable
fun ChangeThemeScreen() {
    val viewModel: ChangeThemeViewModel = provideViewModel()
    FixedTopBarColumnLayout(
        title = stringResource(Res.string.theme_change_title),
        onBack = viewModel::onBack,
        content = {
            ChangeThemeLayout(
                modifier = Modifier.padding(16.dp),
                viewModel = viewModel
            )
        }
    )
}

@Composable
fun ChangeThemeDialog() {
    val viewModel: ChangeThemeViewModel = provideViewModel()
    ChangeThemeLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(ThemeContext.current.primary)
            .padding(24.dp),
        viewModel = viewModel
    )
}

@Composable
fun ChangeThemeLayout(
    modifier: Modifier = Modifier,
    viewModel: ChangeThemeViewModel = provideViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DynamicColorBlock()
        DarkModePreferenceBlock(viewModel)
    }
}

@Composable
fun DynamicColorBlock(viewModel: ChangeThemeViewModel = provideViewModel()) {
    val use = viewModel.dynamicColorsStore.asStateValue() ?: return
    Column {
        HeaderBlock(stringResource(Res.string.theme_change_dynamic_color))
        Spacer8()
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dynamic_color_on),
            selected = use,
            onClick = viewModel::onEnableDynamicColors
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dynamic_color_off),
            selected = !use,
            onClick = viewModel::onDisableDynamicColors
        )
    }
}

@Composable
fun DarkModePreferenceBlock(viewModel: ChangeThemeViewModel = provideViewModel()) {
    val config = viewModel.configStore.asStateValue() ?: return
    Column {
        HeaderBlock(stringResource(Res.string.theme_change_dark_mode))
        Spacer8()
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_system),
            selected = config.autoDark,
            onClick = viewModel::onUseSystemDefault
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_off),
            selected = !config.autoDark && !config.defaultTheme.dark,
            onClick = viewModel::onUseLight
        )
        ToggleBlock(
            label = stringResource(Res.string.theme_change_dark_mode_on),
            selected = !config.autoDark && config.defaultTheme.dark,
            onClick = viewModel::onUseDark
        )
    }
}

@Composable
fun HeaderBlock(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600
    )
}

@Composable
fun ToggleBlock(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = label)
    }
}