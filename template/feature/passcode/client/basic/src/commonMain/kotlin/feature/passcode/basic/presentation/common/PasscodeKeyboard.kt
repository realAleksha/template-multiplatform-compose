package feature.passcode.basic.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shared.presentation.ui.component.DsIcon
import shared.presentation.ui.component.DsText
import shared.presentation.ui.icon.DsIconModel
import shared.presentation.ui.icon.DsIcons
import shared.presentation.ui.theme.DsTheme

private val numberSize = 48.sp
private val actionSize = 72.dp
private val actionSpace = 24.dp

@Composable
internal fun PasscodeKeyboard(
    modifier: Modifier = Modifier,
    codeLength: Int,
    title: String? = null,
    getCode: () -> String?,
    getError: () -> String?,
    onCodeChange: (code: String) -> Unit,
    bottomLeftBlock: @Composable (size: Dp) -> Unit = {},
    bottomRightBlock: @Composable (size: Dp) -> Unit = {
        EraseBlock(getCode, onCodeChange)
    }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(actionSpace, Alignment.CenterVertically)
    ) {
        PasscodeDots(
            title = title,
            getCode = getCode,
            getError = getError,
            codeLength = codeLength
        )
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(1, codeLength, getCode, onCodeChange)
            PadNumberButton(2, codeLength, getCode, onCodeChange)
            PadNumberButton(3, codeLength, getCode, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(4, codeLength, getCode, onCodeChange)
            PadNumberButton(5, codeLength, getCode, onCodeChange)
            PadNumberButton(6, codeLength, getCode, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(7, codeLength, getCode, onCodeChange)
            PadNumberButton(8, codeLength, getCode, onCodeChange)
            PadNumberButton(9, codeLength, getCode, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            Box(modifier = Modifier.size(actionSize)) {
                bottomLeftBlock(actionSize)
            }
            PadNumberButton(0, codeLength, getCode, onCodeChange)
            Box(modifier = Modifier.size(actionSize)) {
                bottomRightBlock(actionSize)
            }
        }
    }
}

@Composable
fun PadTextButton(
    text: String,
    onClick: () -> Unit
) {
    PadButton(onClick = onClick) {
        DsText(
            text = text,
            maxLines = 1,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = DsTheme.current.onSurface,
        )
    }
}

@Composable
fun PadNumberButton(
    number: Int,
    codeLength: Int,
    getCode: () -> String?,
    onCodeChange: (code: String) -> Unit
) {
    PadButton(
        backgroundColor = DsTheme.current.surface,
        onClick = {
            (getCode().orEmpty() + number)
                .takeIf { code -> code.length <= codeLength }
                ?.let(onCodeChange)
        }
    ) {
        DsText(
            color = DsTheme.current.onSurface,
            text = number.toString(),
            fontSize = numberSize,
        )
    }
}

@Composable
fun PadIconButton(
    modifier: Modifier = Modifier,
    icon: DsIconModel,
    onClick: () -> Unit
) {
    PadButton(onClick = onClick) {
        DsIcon(
            tint = DsTheme.current.onSurface,
            modifier = modifier.size(32.dp),
            model = icon
        )
    }
}

@Composable
fun PadButton(
    backgroundColor: Color = Color.Unspecified,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(actionSize)
            .clip(RoundedCornerShape(percent = 50))
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = onClick,
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current
                )
        )
        content()
    }
}

@Composable
fun EraseBlock(
    getCode: () -> String?,
    onCodeChange: (code: String) -> Unit
) {
    Box(modifier = Modifier.size(actionSize)) {
        val code = getCode()
        AnimatedVisibility(
            modifier = Modifier.size(actionSize),
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            visible = !code.isNullOrEmpty()
        ) {
            PadIconButton(
                icon = DsIcons.backspace,
                onClick = {
                    code
                        ?.takeIf { code -> code.isNotEmpty() }
                        ?.let { code -> code.take(code.length - 1) }
                        ?.let(onCodeChange)
                }
            )
        }
    }
}