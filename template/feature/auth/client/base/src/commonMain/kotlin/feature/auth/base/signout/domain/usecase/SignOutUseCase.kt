package feature.auth.base.signout.domain.usecase

import feature.auth.base.common.domain.repository.AuthRepository

internal class SignOutUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun invoke() {
        authRepository.signOut()
    }
}