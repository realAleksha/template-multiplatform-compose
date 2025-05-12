package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.repository.PasscodeRepository
import kotlinx.coroutines.flow.Flow

internal class GetLockStateUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke(): Flow<LockState> {
        return repository.getLockState()
    }

}