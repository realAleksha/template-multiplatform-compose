package shared.data.settings.datastore

import androidx.datastore.core.FileStorage
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import shared.data.common.AppHolder

actual fun createStorage(name: String): Storage<Preferences> {
    return FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { AppHolder.app.filesDir.resolve(name) }
    )
}