package shared.data.settings.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first
import shared.data.encoding.EncodingStrategy
import shared.data.settings.SettingsSource
import kotlin.reflect.KClass

expect fun createStorage(name: String): Storage<Preferences>

/**
 * Settings source implementation using [Jetpack DataStore].
 *
 * https://developer.android.com/kotlin/multiplatform/datastore
 */
@Suppress("UNCHECKED_CAST")
class DataStoreSource(
    private val name: String = "app.preferences_pb"
) : SettingsSource() {

    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(createStorage(name))
    }

    override suspend fun <T : Any> save(
        key: String,
        value: T?,
        encodingStrategy: EncodingStrategy<T>
    ) {
        if (value == null) {
            remove(key, encodingStrategy)
        } else {
            dataStore.edit { prefs ->
                val prefsKey = defineKey(key, encodingStrategy.getType())
                if (prefsKey != null) {
                    prefs[prefsKey] = value
                } else {
                    val jsonKey = stringPreferencesKey(key)
                    prefs[jsonKey] = encodingStrategy.encode(value)
                }
            }
        }
    }

    override suspend fun <T : Any> read(
        key: String,
        encodingStrategy: EncodingStrategy<T>
    ): T? {
        val prefsKey = defineKey(key, encodingStrategy.getType())
        val value = if (prefsKey != null) {
            dataStore.data.first()[prefsKey]
        } else {
            val jsonKey = stringPreferencesKey(key)
            val jsonString = dataStore.data.first()[jsonKey] ?: return null
            encodingStrategy.decode(jsonString)
        }
        return value
    }

    override suspend fun <T : Any> remove(
        key: String,
        encodingStrategy: EncodingStrategy<T>
    ): T? {
        val prev = read(key, encodingStrategy)
        dataStore.edit { prefs ->
            prefs.remove(
                defineKey(key, encodingStrategy.getType())
                    ?: stringPreferencesKey(key)
            )
        }
        return prev
    }

    override suspend fun clear() {
        dataStore.edit { prefs -> prefs.clear() }
    }

    private fun <T : Any> defineKey(key: String, type: KClass<T>): Preferences.Key<T>? {
        val prefsKey = when (type) {
            String::class -> stringPreferencesKey(key)
            Boolean::class -> booleanPreferencesKey(key)
            Int::class -> intPreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            Double::class -> doublePreferencesKey(key)
            ByteArray::class -> byteArrayPreferencesKey(key)
            else -> null
        }
        return prefsKey as? Preferences.Key<T>
    }

}