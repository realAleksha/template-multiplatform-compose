package feature.update.sideload.data.repository

import io.ktor.utils.io.ByteReadChannel

internal actual class PlatformFile {
    actual fun exists(): Boolean = false
    actual fun delete(): Boolean = false
    actual fun getAbsolutePath(): String = ""
    actual suspend fun writeChannel(channel: ByteReadChannel) {}
}

internal actual fun getDownloadFile(): PlatformFile = PlatformFile()

internal actual suspend fun installFromFile(filePath: String) {}
