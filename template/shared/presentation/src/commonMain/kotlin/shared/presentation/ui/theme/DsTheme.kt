package shared.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import shared.presentation.theme.Theme
import shared.presentation.theme.localTheme

@Immutable
data class DsTheme(
    override val id: String,
    override val dark: Boolean,
    override val colorScheme: ColorScheme
) : Theme {

    val error: Color = colorScheme.error
    val surface: Color = colorScheme.surface
    val onSurface: Color = colorScheme.onSurface
    val onSurfaceSecondary: Color = onSurface.copy(alpha = 0.65f)

    val highlightPrimary: Color = colorScheme.onSurface.copy(alpha = 0.15f)
    val highlightSecondary: Color = colorScheme.onSurface.copy(alpha = 0.1f)

    val topBlur by lazy {
        Brush.verticalGradient(
            listOf(
                surface,
                surface.copy(alpha = 0.95f),
                surface.copy(alpha = 0.9f),
                surface.copy(alpha = 0.85f),
            )
        )
    }

    val bottomBlur by lazy {
        Brush.verticalGradient(
            listOf(
                surface.copy(alpha = 0.85f),
                surface.copy(alpha = 0.9f),
                surface.copy(alpha = 0.95f),
                surface
            )
        )
    }

    val shimmerColors by lazy {
        listOf(
            onSurface.copy(alpha = 0.1f),
            onSurface.copy(alpha = 0.05f),
            onSurface.copy(alpha = 0.08f)
        )
    }

    companion object {
        val current: DsTheme
            @Composable
            @ReadOnlyComposable
            get() = currentOrNull ?: error("not available outside of ThemeProvider")

        val currentOrNull: DsTheme?
            @Composable
            @ReadOnlyComposable
            get() = localTheme.current as? DsTheme
    }
}