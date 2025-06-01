package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository

internal class SetPasscodeUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(code: String) {
        repository.lock(code)
    }

}