package shared.data.encoding

import kotlinx.serialization.KSerializer
import shared.data.common.DataSource
import kotlin.reflect.KClass

/**
 * A strategy interface for converting objects of type [T] to and from strings.
 *
 * @param T The type of the object to convert.
 */
interface EncodingStrategy<T : Any> : DataSource {

    /**
     * Converts an object of type [T] to its string representation.
     *
     * @param from The object to convert.
     * @return The string representation of the object.
     */
    fun encode(from: T): String

    /**
     * Converts a string representation to an object of type [T].
     *
     * @param from The string to convert.
     * @return An object of type [T], or null if conversion fails.
     */
    fun decode(from: String): T

    /**
     * Gets the type of the serialized object.
     *
     * @return The class representing the type [T].
     */
    fun getType(): KClass<T>

    companion object {
        inline fun <reified D : Any> default(): EncodingStrategy<D> =
            DefaultEncodingStrategy(D::class)

        inline fun <reified D : Any> json(serializer: KSerializer<D>): EncodingStrategy<D> =
            JsonEncodingStrategy(serializer, D::class)
    }

}