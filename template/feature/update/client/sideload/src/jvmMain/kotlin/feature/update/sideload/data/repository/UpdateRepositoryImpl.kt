package feature.update.sideload.data.repository

import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.copyTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File

internal actual class PlatformFile(val file: File) {
    actual fun exists(): Boolean = file.exists()
    actual fun delete(): Boolean = file.delete()
    actual fun getAbsolutePath(): String = file.absolutePath
    actual suspend fun writeChannel(channel: ByteReadChannel) {
        file.outputStream().use { output ->
            channel.copyTo(output)
        }
    }
}

internal actual fun getDownloadFile(): PlatformFile {
    return PlatformFile(File(System.getProperty("java.io.tmpdir"), "update-file"))
}

internal actual suspend fun installFromFile(filePath: String) {
    val file = File(filePath)
    if (Desktop.isDesktopSupported()) {
        withContext(Dispatchers.IO) {
            Desktop.getDesktop().open(file)
        }
    }
    Runtime.getRuntime().exit(0)
}
