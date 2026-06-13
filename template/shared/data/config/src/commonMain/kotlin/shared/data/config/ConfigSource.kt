package shared.data.config

import shared.data.common.DataSource
import shared.data.encoding.EncodingStrategy

/**
 * Interface for accessing configuration data from various sources.
 *
 * Extends the [DataSource] interface.
 */
interface ConfigSource : DataSource {

    /**
     * Retrieves the value associated with the specified key from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the value for.
     * @param encodingStrategy The strategy, used to serialize/deserialize the provided object.
     * @param defaultValue A lambda function providing the default value to return if the key is not found.
     * @return The value associated with the key, or the default value if the key is not found.
     */
    fun <T : Any> get(
        key: String,
        encodingStrategy: EncodingStrategy<T>,
        defaultValue: () -> T
    ): T

    /**
     * Convenience method for retrieving a string value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the string value for.
     * @param defaultValue A lambda function providing the default string value to return if the key is not found.
     * @return The string value associated with the key, or the default value if the key is not found.
     */
    fun getString(key: String, defaultValue: () -> String): String {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

    /**
     * Convenience method for retrieving a boolean value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the boolean value for.
     * @param defaultValue A lambda function providing the default boolean value to return if the key is not found.
     * @return The boolean value associated with the key, or the default value if the key is not found.
     */
    fun getBoolean(key: String, defaultValue: () -> Boolean): Boolean {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

    /**
     * Convenience method for retrieving a long value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the long value for.
     * @param defaultValue A lambda function providing the default long value to return if the key is not found.
     * @return The long value associated with the key, or the default value if the key is not found.
     */
    fun getLong(key: String, defaultValue: () -> Long): Long {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

    /**
     * Convenience method for retrieving an integer value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the integer value for.
     * @param defaultValue A lambda function providing the default integer value to return if the key is not found.
     * @return The integer value associated with the key, or the default value if the key is not found.
     */
    fun getInt(key: String, defaultValue: () -> Int): Int {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

    /**
     * Convenience method for retrieving a double value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the double value for.
     * @param defaultValue A lambda function providing the default double value to return if the key is not found.
     * @return The double value associated with the key, or the default value if the key is not found.
     */
    fun getDouble(key: String, defaultValue: () -> Double): Double {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

    /**
     * Convenience method for retrieving a float value from the configuration source.
     * If the key is not found, returns the default value provided by the defaultValue lambda function.
     *
     * @param key The key to retrieve the float value for.
     * @param defaultValue A lambda function providing the default float value to return if the key is not found.
     * @return The float value associated with the key, or the default value if the key is not found.
     */
    fun getFloat(key: String, defaultValue: () -> Float): Float {
        return get(key, EncodingStrategy.default(), defaultValue)
    }

}