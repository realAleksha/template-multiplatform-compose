package shared.presentation.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import shared.presentation.ui.theme.DsTheme

@Composable
fun DsText(
    modifier: Modifier = Modifier,
    text: String?,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
) {
    Text(
        modifier = modifier,
        text = text.orEmpty(),
        color = color,
        maxLines = maxLines,
        textAlign = textAlign,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight
    )
}

@Composable
@NonRestartableComposable
fun DsTextHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600
    )
}

@Composable
@NonRestartableComposable
fun DsTextPrimaryHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    DsTextHeader(
        modifier = modifier,
        text = text,
        color = DsTheme.current.colorScheme.primary
    )
}
