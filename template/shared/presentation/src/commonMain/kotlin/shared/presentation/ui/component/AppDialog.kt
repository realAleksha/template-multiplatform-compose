package shared.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import shared.presentation.theme.Theme

@Composable
@NonRestartableComposable
fun AppErrorDialog(title: String, th: Throwable, onClose: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(24.dp),
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        title = {
            Text(text = title)
        },
        text = {
            Text(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                text = th.stackTraceToString()
            )
        },
        confirmButton = {
            AppTextButton(
                text = "OK",
                onClick = onClose
            )
        }
    )
}

@Composable
@NonRestartableComposable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    confirmLabel: String,
    confirmAction: () -> Unit,
    dismissLabel: String? = null,
    dismissAction: (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { AppText(text = title) },
        text = { AppText(text = text) },
        confirmButton = {
            AppTextButton(
                text = confirmLabel,
                onClick = confirmAction
            )
        },
        dismissButton = dismissLabel?.let {
            {
                AppTextButton(
                    text = dismissLabel,
                    onClick = dismissAction!!
                )
            }
        }
    )
}

@Composable
@NonRestartableComposable
fun AppDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun AppDialogContent(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Theme.current.surface)
            .padding(24.dp)
    ) {
        content()
    }
}