package shared.data.encryption.korlibs

import korlibs.crypto.PBKDF2
import shared.data.encryption.EncryptionMethod
import shared.data.encryption.EncryptionResolver
import kotlin.reflect.KClass

/**
 * A resolver for PBKDF2 hashing.
 */
internal class Pbkdf2Resolver : EncryptionResolver<EncryptionMethod.PBKDF2> {

    override val methodType: KClass<EncryptionMethod.PBKDF2> = EncryptionMethod.PBKDF2::class

    override fun encrypt(data: ByteArray, method: EncryptionMethod.PBKDF2): ByteArray {
        val hash = PBKDF2.pbkdf2WithHmacSHA512(
            password = data,
            salt = method.salt.encodeToByteArray(),
            iterationCount = 1000,
            keySizeInBits = 512
        )
        return hash.bytes
    }

    override fun decrypt(data: ByteArray, method: EncryptionMethod.PBKDF2): ByteArray {
        return data
    }
}