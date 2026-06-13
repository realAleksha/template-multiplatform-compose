package shared.data.encryption.korlibs

import korlibs.crypto.AES
import korlibs.crypto.Padding
import shared.data.encryption.EncryptionMethod
import shared.data.encryption.EncryptionResolver
import kotlin.reflect.KClass

/**
 * A resolver for AES encryption and decryption operations.
 *
 * This class provides implementations for encrypting and decrypting data using
 * the AES encryption method with CBC mode and PKCS7 padding.
 */
internal class AesResolver : EncryptionResolver<EncryptionMethod.AES> {

    private val keyLength = 16
    private val keyPadChar = ' '
    private val padding = Padding.PKCS7Padding

    override val methodType: KClass<EncryptionMethod.AES> = EncryptionMethod.AES::class

    override fun encrypt(data: ByteArray, method: EncryptionMethod.AES): ByteArray {
        val key = method.password.padEnd(keyLength, keyPadChar).encodeToByteArray()
        return AES.encryptAesCbc(data, key, key, padding)
    }

    override fun decrypt(data: ByteArray, method: EncryptionMethod.AES): ByteArray {
        val key = method.password.padEnd(keyLength, keyPadChar).encodeToByteArray()
        return AES.decryptAesCbc(data, key, key, padding)
    }
}