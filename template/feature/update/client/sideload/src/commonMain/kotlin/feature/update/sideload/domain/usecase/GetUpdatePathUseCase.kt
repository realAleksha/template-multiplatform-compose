package feature.update.sideload.domain.usecase

import feature.update.sideload.domain.repository.UpdateRepository

internal class GetUpdatePathUseCase(
    private val updateRepository: UpdateRepository
) {
    suspend operator fun invoke(): String? {
        return updateRepository.getUpdatePath()
    }
}
