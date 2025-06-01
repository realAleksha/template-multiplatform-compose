package feature.auth.supabase

import feature.auth.api.AuthFeature
import feature.auth.base.BaseAuthProvider
import feature.auth.base.common.domain.repository.AuthRepository
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import io.github.jan.supabase.SupabaseClient
import org.koin.core.qualifier.StringQualifier

class SupabaseAuthProvider(
    private val client: SupabaseClient
) : BaseAuthProvider(), AuthFeature {

    override val name: String = "Supabase Auth"

    override fun createAuthRepository(): AuthRepository = SupabaseAuthRepository(client)

    override fun createGoogleFlowProvider(): GoogleFlowProvider = SupabaseFlowProvider(koinApp.koin.get(), client)

    companion object {
        val qualifier = StringQualifier("supabase")
    }
}