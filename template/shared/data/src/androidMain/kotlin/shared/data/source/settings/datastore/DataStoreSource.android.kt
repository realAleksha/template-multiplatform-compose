package shared.data.source.settings.datastore

import androidx.datastore.core.FileStorage
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import shared.data.AppHolder

actual fun createStorage(name: String): Storage<Preferences> {
    return FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { AppHolder.app.filesDir.resolve(name) }
    )
}