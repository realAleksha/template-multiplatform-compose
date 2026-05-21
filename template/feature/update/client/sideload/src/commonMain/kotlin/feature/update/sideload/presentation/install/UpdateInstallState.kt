package feature.update.sideload.presentation.install

import androidx.compose.runtime.Stable
import feature.update.sideload.SideloadUpdateState
import shared.presentation.state.UiEvent
import shared.presentation.state.ViewState

@Stable
internal interface UpdateInstallState : ViewState {

    data class OnState(val state: SideloadUpdateState) : UiEvent

}
