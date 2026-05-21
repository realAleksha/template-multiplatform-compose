package feature.update.sideload.presentation.check

import feature.update.sideload.SideloadUpdateState
import feature.update.sideload.SideloadUpdateStateResolver
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_error_unknown

internal class UpdateCheckViewModel(
    private val resolver: SideloadUpdateStateResolver
) : BaseViewModel() {

    private val _state = UpdateCheckMutableState()
    val state: UpdateCheckState = _state

    override fun doBind() {
        onCheck()
    }

    private fun onCheck() = async("onCheck") {
        _state.tryCatch(
            title = "Checking for updates",
            onTry = {
                _state.uiState = UiState.Blocking
                val result = resolver.resolve()
                _state.notify(UpdateCheckState.OnState(result))
            },
            onCatch = { e ->
                val state = SideloadUpdateState.Error(e.message ?: getString(Res.string.update_error_unknown))
                _state.notify(UpdateCheckState.OnState(state))
            }
        )
    }

    private class UpdateCheckMutableState : UpdateCheckState, MutableViewState()
}
