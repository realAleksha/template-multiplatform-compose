package feature.passcode.basic.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Passcode(
    val salt: String,
    val encodedCode: String,
    val unlockAttempts: Int,
    val unlockTime: Long
)