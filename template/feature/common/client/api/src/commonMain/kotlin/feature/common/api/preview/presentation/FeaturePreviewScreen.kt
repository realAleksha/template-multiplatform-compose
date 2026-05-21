package feature.common.api.preview.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import shared.presentation.theme.DefaultThemeState
import shared.presentation.theme.ThemeConfig
import shared.presentation.theme.ThemeProvider
import shared.presentation.ui.component.DsIcon
import shared.presentation.ui.component.DsOutlinedCard
import shared.presentation.ui.component.DsSpacer8
import shared.presentation.ui.component.DsText
import shared.presentation.ui.container.DsFixedTopBarLazyColumn
import shared.presentation.ui.theme.DsTheme
import shared.presentation.ui.theme.DsThemes

@Composable
fun FeaturePreviewScreen(
    title: String,
    previews: List<FeaturePreview>,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val selectedPreviewState = remember { mutableStateOf<FeaturePreview?>(null) }
    DsFixedTopBarLazyColumn(
        modifier = modifier,
        onBack = onBack,
        title = title,
    ) {
        item { DsSpacer8() }
        previews.forEach { feature ->
            featureItem(feature, selectedPreviewState::value::set)
        }
        item { DsSpacer8() }
    }

    selectedPreviewState.value?.let { provider ->
        FeaturePreviewScreen(
            modifier = modifier,
            preview = provider,
            onBack = { selectedPreviewState.value = null }
        )
    }
}

@Composable
private fun ThemeContent(content: @Composable () -> Unit) {
    if (DsTheme.currentOrNull == null) {
        val themeState = remember {
            DefaultThemeState(
                defaultConfig = ThemeConfig(
                    defaultTheme = DsThemes.Light,
                    lightTheme = DsThemes.Light,
                    darkTheme = DsThemes.Dark,
                )
            )
        }
        ThemeProvider(themeState) {
            content()
        }
    } else {
        content()
    }
}

private fun LazyListScope.featureItem(
    preview: FeaturePreview,
    onClick: (FeaturePreview) -> Unit
) {
    item {
        DsOutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onClick(preview) })
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    DsText(
                        text = preview.name,
                        color = DsTheme.current.onSurface
                    )
                    DsText(
                        text = preview.type.simpleName,
                        color = DsTheme.current.onSurfaceSecondary
                    )
                }
                DsIcon(
                    model = FeaturePreviewIcons.chevronRight,
                    tint = DsTheme.current.onSurfaceSecondary
                )
            }
        }
    }
}

private fun LazyListScope.featureMethodItem(
    method: FeatureMethod
) {
    item {
        DsOutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            method.preview()
        }
    }
}

@Composable
fun FeaturePreviewScreen(
    preview: FeaturePreview,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val methods = remember(preview) { preview.getMethods() }

    ThemeContent {
        DsFixedTopBarLazyColumn(
            modifier = modifier,
            title = preview.name,
            onBack = onBack
        ) {
            item { DsSpacer8() }
            methods.forEach { method -> featureMethodItem(method) }
            item { DsSpacer8() }
        }
    }
}
