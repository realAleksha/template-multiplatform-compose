package kotli.app.app.presentation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.common.FeatureProvider
import shared.presentation.ui.component.DsSnackbarState

@Stable
interface AppState {
    val startDestination: Any?
    val transitionDuration: Int
    val snackbarState: DsSnackbarState
    val features: List<FeatureProvider>
}

class AppMutableState(
    override val features: List<FeatureProvider>,
    override val snackbarState: DsSnackbarState,
    override val transitionDuration: Int = 0,
) : AppState {
    override var startDestination: Any? by mutableStateOf(null)
}