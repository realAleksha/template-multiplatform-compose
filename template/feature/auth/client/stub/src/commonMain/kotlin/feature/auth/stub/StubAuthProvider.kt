package feature.auth.stub

import feature.auth.api.AuthFeature
import feature.auth.base.BaseAuthProvider
import feature.auth.base.common.domain.repository.AuthRepository
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import org.koin.core.qualifier.StringQualifier

class StubAuthProvider : BaseAuthProvider(), AuthFeature {

    override val name: String = "Stub Auth"

    override fun createAuthRepository(): AuthRepository = StubAuthRepository()

    override fun createGoogleFlowProvider(): GoogleFlowProvider = StubFlowProvider(koinApp.koin.get())

    companion object {
        val qualifier = StringQualifier("stub")
    }
}