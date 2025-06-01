package feature.common.preview.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import shared.presentation.ui.component.DsDropDown
import shared.presentation.ui.component.DsText

data class MethodChangesOption(
    override val name: String,
    val options: () -> List<String>,
    val getSelectedOption: () -> String,
    val onSelectOption: (String) -> Unit
) : FeatureMethod {

    @Composable
    override fun preview() {
        val selectionState = remember { mutableStateOf(getSelectedOption()) }
        val menuState = remember { mutableStateOf(false) }

        LaunchedEffect(name) {
            snapshotFlow { getSelectedOption() }.collect(selectionState::value::set)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { menuState.value = true }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DsText(text = name)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                DsText(
                    text = selectionState.value,
                    fontWeight = FontWeight.Bold
                )
                Menu(
                    options = options,
                    onChangeValue = onSelectOption,
                    isExpanded = menuState::value::get,
                    onDismissRequest = { menuState.value = false }
                )
            }
        }
    }
}

@Composable
private fun Menu(
    options: () -> List<String>,
    isExpanded: () -> Boolean,
    onDismissRequest: () -> Unit,
    onChangeValue: (String) -> Unit,
) {
    DsDropDown(
        expanded = isExpanded(),
        onDismissRequest = onDismissRequest,
        content = {
            options.invoke().forEach { option ->
                DsText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onChangeValue(option)
                            onDismissRequest()
                        }
                        .padding(16.dp),
                    text = option,
                )
            }
        }
    )
}