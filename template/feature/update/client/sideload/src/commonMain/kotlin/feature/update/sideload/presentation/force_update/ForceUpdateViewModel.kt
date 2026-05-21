package feature.update.sideload.presentation.force_update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.update.sideload.SideloadUpdateState
import feature.update.sideload.domain.usecase.DownloadUpdateUseCase
import feature.update.sideload.domain.usecase.InstallUpdateUseCase
import feature.update.sideload.domain.usecase.SetUpdatePathUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.notify
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_downloading_title
import template.feature.update.client.sideload.generated.resources.update_error_unknown

internal class ForceUpdateViewModel(
    private val downloadUpdateUseCase: DownloadUpdateUseCase,
    private val installUpdateUseCase: InstallUpdateUseCase,
    private val setUpdatePathUseCase: SetUpdatePathUseCase
) : BaseViewModel() {

    private val _state = ForceUpdateMutableState()
    val state: ForceUpdateState = _state

    fun onDownload(url: String) = async("onDownload") {
        _state.tryCatch(
            title = getString(Res.string.update_downloading_title),
            onTry = {
                _state.isDownloading = true
                _state.progress = 0f
                val path = downloadUpdateUseCase(url) { p ->
                    _state.progress = p
                }
                setUpdatePathUseCase(path)
                installUpdateUseCase(path)
            },
            onCatch = { e ->
                val state = SideloadUpdateState.Error(e.message ?: getString(Res.string.update_error_unknown))
                _state.notify(ForceUpdateState.OnState(state))
            }
        )
    }

    private class ForceUpdateMutableState : MutableViewState(), ForceUpdateState {
        override var progress: Float by mutableStateOf(0f)
        override var isDownloading: Boolean by mutableStateOf(false)
    }
}
