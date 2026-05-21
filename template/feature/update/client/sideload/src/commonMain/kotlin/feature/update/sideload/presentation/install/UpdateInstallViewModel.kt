package feature.update.sideload.presentation.install

import feature.update.sideload.SideloadUpdateState
import feature.update.sideload.domain.usecase.InstallUpdateUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_error_unknown
import template.feature.update.client.sideload.generated.resources.update_installing_title

internal class UpdateInstallViewModel(
    private val installUpdateUseCase: InstallUpdateUseCase
) : BaseViewModel() {

    private val _state = UpdateInstallMutableState()
    val state: UpdateInstallState = _state

    fun onInstall(filePath: String) = async("onInstall") {
        _state.tryCatch(
            title = getString(Res.string.update_installing_title),
            onTry = {
                installUpdateUseCase(filePath)
            },
            onCatch = { e ->
                val state = SideloadUpdateState.Error(e.message ?: getString(Res.string.update_error_unknown))
                _state.notify(UpdateInstallState.OnState(state))
            }
        )
    }

    private class UpdateInstallMutableState : UpdateInstallState, MutableViewState()
}
