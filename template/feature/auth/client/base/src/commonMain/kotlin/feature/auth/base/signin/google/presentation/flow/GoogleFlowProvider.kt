package feature.auth.base.signin.google.presentation.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope

interface GoogleFlowProvider {

    @Stable
    @Composable
    fun provide(scope: CoroutineScope): GoogleFlow
}