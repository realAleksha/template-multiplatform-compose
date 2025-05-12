package feature.auth.supabase

import feature.auth.base.BaseAuthProvider
import feature.auth.base.common.domain.repository.AuthRepository
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import io.github.jan.supabase.SupabaseClient
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

class SupabaseAuthProvider(
    private val client: SupabaseClient
) : BaseAuthProvider() {

    override fun Module.withGoogleFlowProvider(): KoinDefinition<GoogleFlowProvider> = single {
        SupabaseFlowProvider(get(), client)
    }

    override fun Module.withAuthRepository(): KoinDefinition<AuthRepository> = single {
        SupabaseAuthRepository(client)
    }
}