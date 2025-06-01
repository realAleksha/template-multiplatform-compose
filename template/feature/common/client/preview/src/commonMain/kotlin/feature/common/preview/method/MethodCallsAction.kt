package feature.common.preview.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.common.preview.FeatureMethod
import shared.presentation.ui.component.DsIcon
import shared.presentation.ui.component.DsText
import shared.presentation.ui.icon.DsIcons

data class MethodCallsAction(
    override val name: String,
    val action: () -> Unit
) : FeatureMethod {

    @Composable
    override fun preview() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = action)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DsText(text = name)
            DsIcon(model = DsIcons.chevronRight)
        }
    }
}