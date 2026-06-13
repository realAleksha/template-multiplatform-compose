package shared.data.encryption

import shared.data.common.DataSource

/**
 * Interface representing a source for encryption and decryption operations.
 */
interface EncryptionSource : DataSource {

    /**
     * Encrypts the given text using the specified encryption method.
     *
     * @param text the plain text to be encrypted.
     * @param method the encryption method to use.
     * @return the encrypted text.
     */
    fun encrypt(text: String, method: EncryptionMethod): String

    /**
     * Decrypts the given text using the specified encryption method.
     *
     * @param text the encrypted text to be decrypted.
     * @param method the encryption method to use.
     * @return the decrypted plain text.
     */
    fun decrypt(text: String, method: EncryptionMethod): String
}