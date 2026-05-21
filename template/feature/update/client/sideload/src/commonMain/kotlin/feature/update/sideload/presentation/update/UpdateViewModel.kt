package feature.update.sideload.presentation.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.update.sideload.domain.usecase.DownloadUpdateUseCase
import feature.update.sideload.domain.usecase.InstallUpdateUseCase
import feature.update.sideload.domain.usecase.SetUpdatePathUseCase
import org.jetbrains.compose.resources.getString
import shared.presentation.state.MutableViewState
import shared.presentation.state.tryCatch
import shared.presentation.viewmodel.BaseViewModel
import template.feature.update.client.sideload.generated.resources.Res
import template.feature.update.client.sideload.generated.resources.update_downloading_title

internal class UpdateViewModel(
    private val downloadUpdateUseCase: DownloadUpdateUseCase,
    private val installUpdateUseCase: InstallUpdateUseCase,
    private val setUpdatePathUseCase: SetUpdatePathUseCase
) : BaseViewModel() {

    private val _state = UpdateMutableState()
    val state: UpdateUiState = _state

    fun onDownload(url: String) = async("onDownload") {
        _state.tryCatch(
            title = getString(Res.string.update_downloading_title),
            onTry = {
                progress = 0f
                val path = downloadUpdateUseCase(url) { p ->
                    progress = p
                }
                setUpdatePathUseCase(path)
                installUpdateUseCase(path)
            }
        )
    }

    private class UpdateMutableState : MutableViewState(), UpdateUiState {
        override var updateType: UpdateType by mutableStateOf(UpdateType.Optional)
        override var progress: Float by mutableStateOf(0f)
        override var updateUrl: String? by mutableStateOf(null)
    }
}
