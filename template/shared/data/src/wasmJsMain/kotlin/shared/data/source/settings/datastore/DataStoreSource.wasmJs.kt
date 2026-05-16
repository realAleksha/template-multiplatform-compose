package shared.data.source.settings.datastore

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.WebLocalStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer

actual fun createStorage(name: String): Storage<Preferences> {
    return WebLocalStorage(
        serializer = PreferencesSerializer,
        name = name
    )
}