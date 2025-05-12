package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository

internal class GetRemainingAttemptsUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(): Int {
        return repository.getRemainingUnlockAttempts()
    }

}