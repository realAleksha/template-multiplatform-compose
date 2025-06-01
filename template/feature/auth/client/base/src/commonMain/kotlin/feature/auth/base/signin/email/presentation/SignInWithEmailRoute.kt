package feature.auth.base.signin.email.presentation

import kotlinx.serialization.Serializable

internal sealed interface SignInWithEmailRoute {

    @Serializable
    object Start : SignInWithEmailRoute

    @Serializable
    data class Verify(val email: String) : SignInWithEmailRoute
}