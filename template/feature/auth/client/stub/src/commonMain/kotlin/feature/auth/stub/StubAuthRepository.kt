package feature.auth.stub

import feature.auth.api.AuthUser
import feature.auth.base.common.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class StubAuthRepository : AuthRepository {

    private val authUserFlow = MutableStateFlow<AuthUser?>(null)

    override fun getAuthUser(): Flow<AuthUser?> {
        return authUserFlow
    }

    override suspend fun signInWithGoogle() {
        val authUser = AuthUser(id = "1", email = "stub@gmail.com")
        authUserFlow.value = authUser
    }

    override suspend fun signInWithEmail(email: String) {
        authUserFlow.value = null
    }

    override suspend fun verifyEmail(email: String, otp: String) {
        val authUser = AuthUser(id = "1", email = email)
        authUserFlow.value = authUser
    }

    override suspend fun signOut() {
        authUserFlow.value = null
    }
}