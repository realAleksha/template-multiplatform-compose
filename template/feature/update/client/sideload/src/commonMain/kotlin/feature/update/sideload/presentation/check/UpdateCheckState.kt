package feature.update.sideload.presentation.check

import androidx.compose.runtime.Stable
import feature.update.sideload.SideloadUpdateState
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
interface UpdateCheckState : ViewState {

    data class OnState(val state: SideloadUpdateState) : UiEvent
}