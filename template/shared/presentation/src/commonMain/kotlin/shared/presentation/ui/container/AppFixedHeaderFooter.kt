package shared.presentation.ui.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import shared.presentation.ui.component.AppSpacerDynamic
import shared.presentation.ui.component.AppSpacerNavigationBar
import shared.presentation.ui.component.AppSpacerStatusBar
import shared.presentation.ui.component.AppVerticalScrollbarProvider
import shared.presentation.theme.Theme

data class AppFixedHeaderFooterAppearance(
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
            backgroundColor: Color = Theme.current.surface,
            headerBrush: Brush? = Theme.current.topBlur,
            footerBrush: Brush? = Theme.current.bottomBlur,
            statusSpacer: Boolean = true,
            navigationSpacer: Boolean = true
        ): AppFixedHeaderFooterAppearance {
            return AppFixedHeaderFooterAppearance(
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
fun AppFixedHeaderFooterColumn(
    modifier: Modifier = Modifier.imePadding(),
    appearance: AppFixedHeaderFooterAppearance = AppFixedHeaderFooterAppearance.default(),
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
fun AppFixedHeaderFooterLazyColumn(
    modifier: Modifier = Modifier,
    appearance: AppFixedHeaderFooterAppearance = AppFixedHeaderFooterAppearance.default(),
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
    appearance: AppFixedHeaderFooterAppearance,
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
            AppSpacerStatusBar()
        }
        content.invoke(this)
    }
}

@Composable
private fun BoxScope.FooterBlock(
    appearance: AppFixedHeaderFooterAppearance,
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
            AppSpacerNavigationBar()
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: AppFixedHeaderFooterAppearance,
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
            AppSpacerDynamic(heightState = headerState)
            content.invoke(this)
            AppSpacerDynamic(heightState = footerState)
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: AppFixedHeaderFooterAppearance,
    headerState: State<Int>,
    footerState: State<Int>,
    content: LazyListScope.() -> Unit
) {
    AppVerticalScrollbarProvider { state ->
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .background(appearance.backgroundColor)
        ) {
            if (headerState.value >= 0 && footerState.value >= 0) {
                item { AppSpacerDynamic(heightState = headerState) }
                content.invoke(this)
                item { AppSpacerDynamic(heightState = footerState) }
            }
        }
    }
}

private fun getDefaultHeight(content: @Composable (ColumnScope.() -> Unit)?): Int {
    if (content == null) return 0
    return -1
}