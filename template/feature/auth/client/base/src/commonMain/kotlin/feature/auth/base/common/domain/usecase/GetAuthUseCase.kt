package feature.auth.base.common.domain.usecase

import feature.auth.api.AuthUser
import feature.auth.base.common.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

internal class GetAuthUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(): Flow<AuthUser?> {
        return authRepository.getAuthUser()
    }
}