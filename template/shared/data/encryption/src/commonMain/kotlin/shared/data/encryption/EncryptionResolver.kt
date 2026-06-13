package shared.data.encryption

import kotlin.reflect.KClass

/**
 * Interface representing a resolver for encryption and decryption operations using a specific encryption method.
 *
 * @param <M> the type of the encryption method used.
 */
interface EncryptionResolver<M : EncryptionMethod> {

    /**
     * The type of the encryption method that this resolver supports.
     */
    val methodType: KClass<M>

    /**
     * Encrypts the given data using the specified encryption method.
     *
     * @param data the plain data to be encrypted as a ByteArray.
     * @param method the encryption method to use.
     * @return the encrypted data as a ByteArray.
     */
    fun encrypt(data: ByteArray, method: M): ByteArray

    /**
     * Decrypts the given data using the specified encryption method.
     *
     * @param data the encrypted data to be decrypted as a ByteArray.
     * @param method the encryption method to use.
     * @return the decrypted data as a ByteArray.
     */
    fun decrypt(data: ByteArray, method: M): ByteArray
}