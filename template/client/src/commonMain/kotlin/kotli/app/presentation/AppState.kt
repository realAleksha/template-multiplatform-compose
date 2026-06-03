package kotli.app.presentation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.common.api.FeatureContext

@Stable
interface AppState {
    val start: Any?
    val context: FeatureContext
    fun setStartDestination(start: Any)
}

class AppMutableState(override val context: FeatureContext) : AppState {
    override var start: Any? by mutableStateOf(null)
    override fun setStartDestination(start: Any) = this::start.set(start)
}