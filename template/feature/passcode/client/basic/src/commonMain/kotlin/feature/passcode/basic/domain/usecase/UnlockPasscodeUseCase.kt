package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.repository.PasscodeRepository

internal class UnlockPasscodeUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(code: String): LockState {
        return repository.unlock(code)
    }

}