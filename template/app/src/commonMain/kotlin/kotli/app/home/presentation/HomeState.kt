package kotli.app.home.presentation

import androidx.compose.runtime.Stable
import feature.common.api.Feature

@Stable
interface HomeState {

    val features: List<Feature>
}