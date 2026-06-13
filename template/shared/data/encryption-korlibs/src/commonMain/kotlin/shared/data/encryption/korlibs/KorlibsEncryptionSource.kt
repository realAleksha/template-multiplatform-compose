package shared.data.encryption.korlibs

import shared.data.encryption.BaseEncryptionSource
import shared.data.encryption.EncryptionMethod
import shared.data.encryption.EncryptionSource

/**
 * An implementation of the [EncryptionSource] interface using the Korlibs encryption library.
 *
 * This class uses a basic encryption source with an [AesResolver] to perform encryption
 * and decryption operations.
 */
class KorlibsEncryptionSource : EncryptionSource {

    private val source = BaseEncryptionSource(
        AesResolver(),
        Pbkdf2Resolver()
    )

    override fun encrypt(text: String, method: EncryptionMethod): String {
        return source.encrypt(text, method)
    }

    override fun decrypt(text: String, method: EncryptionMethod): String {
        return source.decrypt(text, method)
    }
}