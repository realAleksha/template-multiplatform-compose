package feature.auth.api

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val id: String,
    val email: String? = null,
    val phone: String? = null,
    val avatarUrl: String? = null,
) {
    fun getFirstLetter(): String = when {
        !email.isNullOrEmpty() -> email.first().uppercaseChar().toString()
        !phone.isNullOrEmpty() -> phone.first().uppercaseChar().toString()
        else -> "?"
    }
}