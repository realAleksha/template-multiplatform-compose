package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository
import shared.data.source.settings.SettingsSource

internal class ForgotPasscodeUseCase(
    private val repository: PasscodeRepository,
    private val settingsSource: SettingsSource,
) {

    suspend fun invoke() {
        settingsSource.clear()
        repository.reset()
    }

}