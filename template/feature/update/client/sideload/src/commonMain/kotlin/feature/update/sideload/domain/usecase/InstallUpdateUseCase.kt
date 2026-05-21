package feature.update.sideload.domain.usecase

import feature.update.sideload.domain.repository.UpdateRepository

internal class InstallUpdateUseCase(
    private val repository: UpdateRepository
) {
    suspend operator fun invoke(filePath: String) = repository.install(filePath)
}
