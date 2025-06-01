package kotli.app.app.presentation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.common.api.Feature

@Stable
interface AppState {
    val startDestination: Any?
    val features: List<Feature>
}

class AppMutableState(override val features: List<Feature>) : AppState {
    override var startDestination: Any? by mutableStateOf(null)
}