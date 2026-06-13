package shared.data.encryption

@Suppress("UNCHECKED_CAST")
open class BaseEncryptionSource(
    vararg resolvers: EncryptionResolver<*>
) : EncryptionSource {

    private val byType by lazy { resolvers.associateBy { resolver -> resolver.methodType } }

    override fun encrypt(text: String, method: EncryptionMethod): String {
        val encoded = get(method).encrypt(text.encodeToByteArray(), method)
        return encoded.toHexString()
    }

    override fun decrypt(text: String, method: EncryptionMethod): String {
        val decoded = get(method).decrypt(text.hexToByteArray(), method)
        return decoded.decodeToString()
    }

    private fun get(method: EncryptionMethod): EncryptionResolver<EncryptionMethod> {
        val type = method::class
        val resolver = byType[type] ?: error("unknown type $type")
        return resolver as EncryptionResolver<EncryptionMethod>
    }
}