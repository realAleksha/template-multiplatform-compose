package feature.auth.base.signin.google.domain.usecase

import feature.auth.base.common.domain.repository.AuthRepository

class SignInWithGoogleUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun invoke() {
        authRepository.signInWithGoogle()
    }
}