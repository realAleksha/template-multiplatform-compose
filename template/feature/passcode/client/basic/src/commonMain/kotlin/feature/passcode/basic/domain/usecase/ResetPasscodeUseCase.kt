package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository

internal class ResetPasscodeUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke() {
        repository.reset()
    }
}