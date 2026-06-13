package shared.data.encoding

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

/**
 * A [EncodingStrategy] implementation that uses JSON serialization for conversion.
 *
 * @param serializer The serializer for the object type [T].
 */
data class JsonEncodingStrategy<T : Any>(
    private val serializer: KSerializer<T>,
    private val type: KClass<T>
) : EncodingStrategy<T> {

    /**
     * Decodes a JSON string into an object of type [T].
     *
     * @param from The JSON string to decode.
     * @return The decoded object of type [T].
     */
    override fun decode(from: String): T {
        return Json.decodeFromString(serializer, from)
    }

    /**
     * Encodes an object of type [T] into a JSON string.
     *
     * @param from The object to encode.
     * @return The JSON string representation of the object.
     */
    override fun encode(from: T): String {
        return Json.encodeToString(serializer, from)
    }

    /**
     * Gets the type of the serialized object.
     *
     * @return The class representing the type [T].
     */
    override fun getType(): KClass<T> {
        return type
    }

}
