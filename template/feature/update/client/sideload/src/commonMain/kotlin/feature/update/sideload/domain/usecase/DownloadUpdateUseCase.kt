package feature.update.sideload.domain.usecase

import feature.update.sideload.domain.repository.UpdateRepository

internal class DownloadUpdateUseCase(
    private val repository: UpdateRepository
) {
    suspend operator fun invoke(url: String, onProgress: (Float) -> Unit): String =
        repository.download(url, onProgress)
}
