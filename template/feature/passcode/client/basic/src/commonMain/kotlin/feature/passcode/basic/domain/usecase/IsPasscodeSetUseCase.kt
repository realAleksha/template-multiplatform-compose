package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository

internal class IsPasscodeSetUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(): Boolean {
        return repository.getPasscode() != null
    }

}