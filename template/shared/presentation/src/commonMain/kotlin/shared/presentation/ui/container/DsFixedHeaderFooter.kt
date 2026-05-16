package shared.presentation.ui.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import shared.presentation.ui.component.DsSpacerDynamic
import shared.presentation.ui.component.DsSpacerNavigationBar
import shared.presentation.ui.component.DsSpacerStatusBar
import shared.presentation.ui.theme.DsTheme

data class DsFixedHeaderFooterAppearance(
    val backgroundColor: Color,
    val headerBrush: Brush,
    val footerBrush: Brush,
    val statusSpacer: Boolean,
    val navigationSpacer: Boolean
) {
    companion object {
        @Stable
        @Composable
        @ReadOnlyComposable
        fun default(
            backgroundColor: Color = DsTheme.current.surface,
            headerBrush: Brush? = DsTheme.current.topBlur,
            footerBrush: Brush? = DsTheme.current.bottomBlur,
            statusSpacer: Boolean = true,
            navigationSpacer: Boolean = true
        ): DsFixedHeaderFooterAppearance {
            return DsFixedHeaderFooterAppearance(
                backgroundColor = backgroundColor,
                headerBrush = headerBrush ?: SolidColor(Color.Unspecified),
                footerBrush = footerBrush ?: SolidColor(Color.Unspecified),
                statusSpacer = statusSpacer,
                navigationSpacer = navigationSpacer
            )
        }
    }
}

@Composable
@NonRestartableComposable
fun DsFixedHeaderFooterColumn(
    modifier: Modifier = Modifier.imePadding(),
    appearance: DsFixedHeaderFooterAppearance = DsFixedHeaderFooterAppearance.default(),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val headerHeight = remember { mutableStateOf(getDefaultHeight(header)) }
    val footerHeight = remember { mutableStateOf(getDefaultHeight(footer)) }
    Box(modifier = modifier.fillMaxSize()) {
        ContentBlock(appearance, headerHeight, footerHeight, content)
        HeaderBlock(appearance, headerHeight, header)
        FooterBlock(appearance, footerHeight, footer)
    }
}

@Composable
@NonRestartableComposable
fun DsFixedHeaderFooterLazyColumn(
    modifier: Modifier = Modifier,
    appearance: DsFixedHeaderFooterAppearance = DsFixedHeaderFooterAppearance.default(),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    val headerHeight = remember { mutableStateOf(getDefaultHeight(header)) }
    val footerHeight = remember { mutableStateOf(getDefaultHeight(footer)) }
    Box(modifier = modifier.fillMaxSize()) {
        ContentBlock(appearance, headerHeight, footerHeight, content)
        HeaderBlock(appearance, headerHeight, header)
        FooterBlock(appearance, footerHeight, footer)
    }
}

@Composable
private fun BoxScope.HeaderBlock(
    appearance: DsFixedHeaderFooterAppearance,
    state: MutableState<Int>,
    content: @Composable (ColumnScope.() -> Unit)?
) {
    if (content == null) return
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .background(appearance.headerBrush)
            .onSizeChanged { state.value = it.height }
    ) {
        if (appearance.statusSpacer) {
            DsSpacerStatusBar()
        }
        content.invoke(this)
    }
}

@Composable
private fun BoxScope.FooterBlock(
    appearance: DsFixedHeaderFooterAppearance,
    state: MutableState<Int>,
    content: @Composable (ColumnScope.() -> Unit)?
) {
    if (content == null) return
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(appearance.footerBrush)
            .onSizeChanged { state.value = it.height }
    ) {
        content.invoke(this)
        if (appearance.navigationSpacer) {
            DsSpacerNavigationBar()
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: DsFixedHeaderFooterAppearance,
    headerState: State<Int>,
    footerState: State<Int>,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appearance.backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        if (headerState.value >= 0 && footerState.value >= 0) {
            DsSpacerDynamic(heightState = headerState)
            content.invoke(this)
            DsSpacerDynamic(heightState = footerState)
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: DsFixedHeaderFooterAppearance,
    headerState: State<Int>,
    footerState: State<Int>,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(appearance.backgroundColor)
    ) {
        if (headerState.value >= 0 && footerState.value >= 0) {
            item { DsSpacerDynamic(heightState = headerState) }
            content.invoke(this)
            item { DsSpacerDynamic(heightState = footerState) }
        }
    }
}

private fun getDefaultHeight(content: @Composable (ColumnScope.() -> Unit)?): Int {
    if (content == null) return 0
    return -1
}