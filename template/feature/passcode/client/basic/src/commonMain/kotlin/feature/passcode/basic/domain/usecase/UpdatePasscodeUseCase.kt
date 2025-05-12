package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.model.LockState
import feature.passcode.basic.domain.repository.PasscodeRepository
import kotlinx.coroutines.flow.first
import kotlin.time.Clock

internal class UpdatePasscodeUseCase(
    private val repository: PasscodeRepository
) {

    suspend fun invoke() {
        val passcode = repository.getPasscode() ?: return
        val lockState = repository.getLockState().first()
        if (lockState != LockState.UNLOCKED) return

        val currentTime = Clock.System.now().toEpochMilliseconds()
        val newPasscode = passcode.copy(unlockTime = currentTime)

        repository.setPasscode(newPasscode)
    }

}