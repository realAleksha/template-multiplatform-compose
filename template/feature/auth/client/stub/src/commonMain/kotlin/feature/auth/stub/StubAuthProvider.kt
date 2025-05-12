package feature.auth.stub

import feature.auth.base.BaseAuthProvider
import feature.auth.base.common.domain.repository.AuthRepository
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import feature.common.FeatureProvider
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf

class StubAuthProvider : BaseAuthProvider(), FeatureProvider {

    override fun Module.withAuthRepository(): KoinDefinition<AuthRepository> =
        singleOf(::StubAuthRepository)

    override fun Module.withGoogleFlowProvider(): KoinDefinition<GoogleFlowProvider> =
        singleOf(::StubFlowProvider)
}