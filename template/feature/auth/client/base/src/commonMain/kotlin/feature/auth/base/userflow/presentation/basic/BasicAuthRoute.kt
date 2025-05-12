package feature.auth.base.userflow.presentation.basic

import kotlinx.serialization.Serializable

@Serializable
internal data class BasicAuthRoute(
    val title: String?
)