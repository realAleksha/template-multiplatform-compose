package feature.update.sideload.presentation.error

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import shared.presentation.state.MutableViewState

internal class UpdateErrorState : MutableViewState() {
    var message by mutableStateOf("")
}
