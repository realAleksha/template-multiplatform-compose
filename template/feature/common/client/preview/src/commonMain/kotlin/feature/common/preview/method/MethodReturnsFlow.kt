package feature.common.preview.method

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import feature.common.preview.FeatureMethod
import kotlinx.coroutines.flow.Flow
import shared.presentation.ui.component.DsText

data class MethodReturnsFlow(
    override val name: String,
    val flow: Flow<String>
) : FeatureMethod {

    @Composable
    override fun preview() {
        val valueState = remember { mutableStateOf("") }

        LaunchedEffect(name) {
            flow.collect(valueState::value::set)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DsText(
                text = name
            )
            DsText(
                text = valueState.value,
                fontWeight = FontWeight.Bold
            )
        }
    }
}