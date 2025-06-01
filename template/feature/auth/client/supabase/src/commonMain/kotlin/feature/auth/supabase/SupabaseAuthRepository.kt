package feature.auth.supabase

import feature.auth.api.AuthUser
import feature.auth.base.common.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class SupabaseAuthRepository(private val client: SupabaseClient) : AuthRepository {

    private val auth by lazy { client.auth }

    override fun getAuthUser(): Flow<AuthUser?> = flow {
        auth.awaitInitialization()
        auth.sessionStatus
            .map { auth.currentUserOrNull() }
            .collect { user ->
                val authUser = user?.let(::map)
                emit(authUser)
            }
    }

    override suspend fun signInWithEmail(email: String) = tryCatch {
        auth.signInWith(OTP) {
            this.email = email
        }
    }

    override suspend fun verifyEmail(email: String, otp: String) = tryCatch {
        auth.verifyEmailOtp(OtpType.Email.EMAIL, email = email, token = otp)
    }

    override suspend fun signInWithGoogle() = tryCatch {
        auth.signInWith(Google)
    }

    override suspend fun signOut() = tryCatch {
        auth.signOut()
        auth.clearSession()
        auth.sessionStatus.first { it is SessionStatus.NotAuthenticated }
    }

    private fun map(from: UserInfo): AuthUser = AuthUser(
        id = from.id,
        email = from.email,
        phone = from.phone
    )

    private suspend fun tryCatch(block: suspend () -> Unit) {
        try {
            block()
        } catch (e: AuthRestException) {
            error(e.errorDescription)
        }
    }
}