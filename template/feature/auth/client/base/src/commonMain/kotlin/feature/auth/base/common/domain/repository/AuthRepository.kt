package feature.auth.base.common.domain.repository

import feature.auth.api.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getAuthUser(): Flow<AuthUser?>

    suspend fun signInWithEmail(email: String)

    suspend fun verifyEmail(email: String, otp: String)

    suspend fun signInWithGoogle()

    suspend fun signOut()
}