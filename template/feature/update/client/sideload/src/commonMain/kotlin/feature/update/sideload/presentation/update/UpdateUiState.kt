package feature.update.sideload.presentation.update

import androidx.compose.runtime.Stable
import shared.presentation.state.ViewState

@Stable
internal interface UpdateUiState : ViewState {
    val updateType: UpdateType
    val progress: Float
    val updateUrl: String?
}

internal enum class UpdateType {
    Idle, Checking, Optional, Force, NoUpdate
}
