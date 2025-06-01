package feature.auth.base.signin.email.domain.usecase

import feature.auth.base.common.domain.repository.AuthRepository

internal class VerifyEmailUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(email: String, otp: String) {
        authRepository.verifyEmail(email, otp)
    }
}