import androidx.compose.ui.window.ComposeViewport
import kotli.app.presentation.App
import org.jetbrains.skiko.wasm.onWasmReady

fun main() = onWasmReady {
    ComposeViewport {
        App()
    }
}