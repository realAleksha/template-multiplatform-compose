package feature.passcode.basic.domain.repository

import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.model.Passcode
import kotlinx.coroutines.flow.Flow

internal interface PasscodeRepository {

    fun getPasscodeLength(): Int

    suspend fun getRemainingUnlockAttempts(): Int

    suspend fun getLockState(): Flow<LockState>

    suspend fun unlock(code: String): LockState

    suspend fun check(code: String): LockState

    suspend fun setPasscode(passcode: Passcode)

    suspend fun getPasscode(): Passcode?

    suspend fun lock(code: String)

    suspend fun reset()

}