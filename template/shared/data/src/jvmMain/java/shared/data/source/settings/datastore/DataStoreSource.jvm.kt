package shared.data.source.settings.datastore

import androidx.datastore.core.FileStorage
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import java.io.File

actual fun createStorage(name: String): Storage<Preferences> {
    return FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { File(System.getProperty("java.io.tmpdir"), name) }
    )
}