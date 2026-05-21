package feature.update.sideload.presentation.force_update

import androidx.compose.runtime.Stable
import feature.update.sideload.SideloadUpdateState
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface ForceUpdateState : ViewState {
    val progress: Float
    val isDownloading: Boolean

    data class OnState(val state: SideloadUpdateState) : UiEvent
}
