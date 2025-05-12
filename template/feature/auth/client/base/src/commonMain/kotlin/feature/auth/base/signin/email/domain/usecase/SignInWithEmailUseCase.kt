package feature.auth.base.signin.email.domain.usecase

import feature.auth.base.common.domain.repository.AuthRepository

internal class SignInWithEmailUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(email: String) {
        authRepository.signInWithEmail(email)
    }
}