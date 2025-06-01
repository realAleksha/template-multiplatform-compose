package feature.auth.supabase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import feature.auth.base.signin.google.domain.usecase.SignInWithGoogleUseCase
import feature.auth.base.signin.google.presentation.flow.GoogleFlow
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.CoroutineScope

internal class SupabaseFlowProvider(
    private val signInWithGoogle: SignInWithGoogleUseCase,
    private val client: SupabaseClient
) : GoogleFlowProvider {

    @Stable
    @Composable
    override fun provide(scope: CoroutineScope): GoogleFlow {
        val action = client.composeAuth.rememberSignInWithGoogle(
            fallback = signInWithGoogle::invoke
        )
        return GoogleFlow { action.startFlow() }
    }
}