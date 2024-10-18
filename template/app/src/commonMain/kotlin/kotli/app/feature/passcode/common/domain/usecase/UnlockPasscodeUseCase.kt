package kotli.app.feature.passcode.common.domain.usecase

import kotli.app.feature.passcode.common.domain.model.LockState
import kotli.app.feature.passcode.common.domain.repository.PasscodeRepository

class UnlockPasscodeUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(code: String): LockState {
        return repository.unlock(code)
    }

}