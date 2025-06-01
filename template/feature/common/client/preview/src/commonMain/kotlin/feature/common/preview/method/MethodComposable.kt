package feature.common.preview.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.common.preview.FeatureMethod
import shared.presentation.ui.component.DsDialog
import shared.presentation.ui.component.DsDialogContent
import shared.presentation.ui.component.DsIcon
import shared.presentation.ui.component.DsText
import shared.presentation.ui.icon.DsIcons

data class MethodComposable(
    override val name: String,
    val composable: @Composable () -> Unit
) : FeatureMethod {

    @Composable
    override fun preview() {
        val shown = remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { shown.value = !shown.value })
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DsText(text = name)
            DsIcon(model = DsIcons.chevronRight)
        }

        if (shown.value) {
            DsDialog(
                modifier = Modifier.size(96.dp),
                onDismissRequest = { shown.value = false },
                content = {
                    DsDialogContent(
                        content = { composable() }
                    )
                }
            )
        }
    }
}