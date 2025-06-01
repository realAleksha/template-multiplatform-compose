package feature.common.preview.method

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import feature.common.preview.FeatureMethod
import shared.presentation.ui.component.DsText
import shared.presentation.ui.theme.DsTheme

data class MethodReturnsValue(
    override val name: String,
    val value: () -> String
) : FeatureMethod {

    @Composable
    override fun preview() {
        val valueState = remember { mutableStateOf(value()) }

        LaunchedEffect(name) {
            snapshotFlow { value() }.collect(valueState::value::set)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DsText(text = name)
            DsText(
                text = valueState.value,
                fontWeight = FontWeight.Bold,
                color = DsTheme.current.onSurfaceSecondary
            )
        }
    }
}