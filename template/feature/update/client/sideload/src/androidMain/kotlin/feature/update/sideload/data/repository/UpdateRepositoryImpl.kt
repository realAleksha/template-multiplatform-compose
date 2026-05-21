package feature.update.sideload.data.repository

import android.content.Intent
import androidx.core.content.FileProvider
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.copyTo
import shared.data.AppHolder
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
    return PlatformFile(File(AppHolder.app.cacheDir, "update.apk"))
}

internal actual suspend fun installFromFile(filePath: String) {
    val file = File(filePath)
    val context = AppHolder.app
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/vnd.android.package-archive")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
