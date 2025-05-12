package feature.auth.base.common.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import feature.auth.api.AuthUser
import shared.presentation.ui.theme.DsTheme
import shared.presentation.ui.component.DsText

@Composable
internal fun AuthUserAvatar(
    modifier: Modifier = Modifier,
    model: AuthUser,
    size: Dp = 64.dp,
    showEmail: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(size.div(6))
    ) {
        FirstLetterAvatar(model, size)

        if (showEmail && model.email != null) {
            DsText(
                text = model.email,
                color = DsTheme.current.onSurface,
                fontSize = remember(size) { size.value.times(0.3).sp },
            )
        }
    }
}

@Composable
private fun FirstLetterAvatar(
    model: AuthUser,
    size: Dp,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(DsTheme.current.highlightPrimary),
        contentAlignment = Alignment.Center
    ) {
        DsText(
            textAlign = TextAlign.Center,
            text = model.getFirstLetter(),
            fontWeight = FontWeight.Medium,
            color = DsTheme.current.onSurface,
            fontSize = remember(size) { size.value.times(0.55).sp }
        )
    }
}
