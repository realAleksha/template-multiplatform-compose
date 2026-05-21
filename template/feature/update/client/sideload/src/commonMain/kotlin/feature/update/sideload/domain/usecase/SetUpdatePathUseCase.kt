package feature.update.sideload.domain.usecase

import feature.update.sideload.domain.repository.UpdateRepository

internal class SetUpdatePathUseCase(
    private val updateRepository: UpdateRepository
) {
    suspend operator fun invoke(path: String?) {
        updateRepository.setUpdatePath(path)
    }
}
