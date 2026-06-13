package shared.data.encryption

/**
 * A sealed class representing different encryption methods.
 */
sealed class EncryptionMethod {

    /**
     * AES encryption method with a specified password.
     */
    data class AES(val password: String) : EncryptionMethod()

    /**
     * PBKDF2 hashing method with a specified salt.
     */
    data class PBKDF2(val salt: String) : EncryptionMethod()
}