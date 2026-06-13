package shared.data.encoding

import kotlin.io.encoding.Base64
import kotlin.reflect.KClass

/**
 * A [EncodingStrategy] implementation that serialize only ByteArray.
 */
object ByteArrayEncodingStrategy : EncodingStrategy<ByteArray> {

    /**
     * Decodes a Base64 URL-safe encoded string into a ByteArray.
     *
     * @param from The Base64 URL-safe encoded string to decode.
     * @return The decoded ByteArray.
     */
    override fun decode(from: String): ByteArray {
        return Base64.UrlSafe.decode(from)
    }

    /**
     * Encodes a ByteArray into a Base64 URL-safe encoded string.
     *
     * @param from The ByteArray to encode.
     * @return The Base64 URL-safe encoded string.
     */
    override fun encode(from: ByteArray): String {
        return Base64.UrlSafe.encode(from)
    }

    /**
     * Gets the type of the serialized object.
     *
     * @return The class representing the ByteArray type.
     */
    override fun getType(): KClass<ByteArray> {
        return ByteArray::class
    }

}
