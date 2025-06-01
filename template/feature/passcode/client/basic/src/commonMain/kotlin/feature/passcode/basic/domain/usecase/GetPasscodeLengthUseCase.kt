package feature.passcode.basic.domain.usecase

import feature.passcode.basic.domain.repository.PasscodeRepository

internal class GetPasscodeLengthUseCase(
    private val repository: PasscodeRepository
) {

    fun invoke(): Int {
        return repository.getPasscodeLength()
    }

}