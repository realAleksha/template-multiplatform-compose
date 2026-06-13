package feature.update.sideload.data.repository

import feature.update.sideload.domain.repository.UpdateRepository
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import shared.data.http.HttpSource
import shared.data.settings.SettingsSource

internal class UpdateRepositoryImpl(
    private val httpSource: HttpSource,
    private val settingsSource: SettingsSource
) : UpdateRepository {

    override suspend fun download(url: String, onProgress: (Float) -> Unit): String {
        val file = getDownloadFile()
        if (file.exists()) file.delete()

        val response = httpSource.client.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                val total = contentLength ?: -1L
                if (total > 0) {
                    onProgress(bytesSentTotal.toFloat() / total)
                }
            }
        }

        val channel = response.bodyAsChannel()
        file.writeChannel(channel)

        return file.getAbsolutePath()
    }

    override suspend fun install(filePath: String) {
        installFromFile(filePath)
    }

    override suspend fun getUpdatePath(): String? {
        return settingsSource.read<String>(KEY)
    }

    override suspend fun setUpdatePath(path: String?) {
        if (path == null) {
            settingsSource.remove(KEY)
        } else {
            settingsSource.save(KEY, path)
        }
    }

    companion object {
        private const val KEY = "sideload_update_file_path"
    }
}

internal expect class PlatformFile {
    fun exists(): Boolean
    fun delete(): Boolean
    fun getAbsolutePath(): String
    suspend fun writeChannel(channel: ByteReadChannel)
}

internal expect fun getDownloadFile(): PlatformFile
internal expect suspend fun installFromFile(filePath: String)
