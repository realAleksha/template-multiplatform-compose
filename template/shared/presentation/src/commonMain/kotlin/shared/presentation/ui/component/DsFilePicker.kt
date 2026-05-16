package shared.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.size

private const val MAX_FILES = 50

data class DsFilePickerFile(
    val name: String,
    val getSize: () -> Long?,
    val readBytes: suspend () -> ByteArray,
)

@Stable
fun interface DsFilePickerLauncher {
    fun launch()
}

@Composable
@NonRestartableComposable
fun getFileLauncher(
    maxFiles: Int = MAX_FILES,
    title: String? = null,
    extensions: Set<String>? = null,
    onResult: (files: List<DsFilePickerFile>) -> Unit
): DsFilePickerLauncher {
    return getLauncher(
        type = FileKitType.File(extensions),
        maxFiles = maxFiles,
        onResult = onResult,
        title = title,
    )
}

@Composable
@NonRestartableComposable
fun getImageLauncher(
    maxFiles: Int = MAX_FILES,
    title: String? = null,
    onResult: (files: List<DsFilePickerFile>) -> Unit
): DsFilePickerLauncher {
    return getLauncher(
        type = FileKitType.Image,
        maxFiles = maxFiles,
        onResult = onResult,
        title = title,
    )
}

@Composable
@NonRestartableComposable
fun getVideoLauncher(
    maxFiles: Int = MAX_FILES,
    title: String? = null,
    onResult: (files: List<DsFilePickerFile>) -> Unit
): DsFilePickerLauncher {
    return getLauncher(
        type = FileKitType.Video,
        maxFiles = maxFiles,
        onResult = onResult,
        title = title,
    )
}

@Composable
@NonRestartableComposable
fun getMediaLauncher(
    maxFiles: Int = MAX_FILES,
    title: String? = null,
    onResult: (files: List<DsFilePickerFile>) -> Unit
): DsFilePickerLauncher {
    return getLauncher(
        type = FileKitType.ImageAndVideo,
        maxFiles = maxFiles,
        onResult = onResult,
        title = title,
    )
}

private fun PlatformFile.toAppFile() = DsFilePickerFile(
    name = name,
    getSize = this::size,
    readBytes = this::readBytes
)

@Composable
private fun getLauncher(
    maxFiles: Int = MAX_FILES,
    title: String? = null,
    type: FileKitType = FileKitType.File(),
    mode: FileKitMode<List<PlatformFile>?, List<PlatformFile>?> = FileKitMode.Multiple(maxFiles),
    onResult: (files: List<DsFilePickerFile>) -> Unit
): DsFilePickerLauncher {
    val launcher = rememberFilePickerLauncher(mode = mode, type = type) { files ->
        val appFiles = files
            ?.take(maxFiles)
            ?.map { file -> file.toAppFile() } ?: emptyList()
        onResult.invoke(appFiles)
    }

    return remember(launcher) {
        DsFilePickerLauncher {
            launcher.launch()
        }
    }
}