package feature.auth.stub

import androidx.compose.runtime.Composable
import feature.auth.base.signin.google.domain.usecase.SignInWithGoogleUseCase
import feature.auth.base.signin.google.presentation.flow.GoogleFlow
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class StubFlowProvider(
    private val signInWithGoogle: SignInWithGoogleUseCase,
) : GoogleFlowProvider {

    @Composable
    override fun provide(scope: CoroutineScope): GoogleFlow = GoogleFlow {
        scope.launch { signInWithGoogle.invoke() }
    }
}