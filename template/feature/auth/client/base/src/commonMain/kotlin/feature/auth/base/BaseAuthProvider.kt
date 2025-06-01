package feature.auth.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import feature.auth.api.AuthFeature
import feature.auth.api.AuthUser
import feature.auth.base.common.domain.repository.AuthRepository
import feature.auth.base.common.domain.usecase.GetAuthUseCase
import feature.auth.base.otp.presentation.OtpScreen
import feature.auth.base.otp.presentation.OtpViewModel
import feature.auth.base.signin.email.domain.usecase.SignInWithEmailUseCase
import feature.auth.base.signin.email.domain.usecase.VerifyEmailUseCase
import feature.auth.base.signin.email.presentation.SignInWithEmailRoute
import feature.auth.base.signin.email.presentation.SignInWithEmailScreen
import feature.auth.base.signin.email.presentation.SignInWithEmailViewModel
import feature.auth.base.signin.google.domain.usecase.SignInWithGoogleUseCase
import feature.auth.base.signin.google.presentation.SignInWithGoogleViewModel
import feature.auth.base.signin.google.presentation.flow.GoogleFlowProvider
import feature.auth.base.signout.domain.usecase.SignOutUseCase
import feature.auth.base.signout.presentation.SignOutDialog
import feature.auth.base.signout.presentation.SignOutRoute
import feature.auth.base.signout.presentation.SignOutViewModel
import feature.auth.base.userflow.presentation.basic.BasicAuthRoute
import feature.auth.base.userflow.presentation.basic.BasicAuthScreen
import feature.auth.base.userflow.presentation.basic.BasicAuthViewModel
import feature.common.api.Feature
import feature.common.api.FeatureContext
import feature.common.koin.KoinFeatureProvider
import feature.common.preview.FeatureMethod
import feature.common.preview.FeaturePreviewProvider
import feature.common.preview.method.MethodCallsAction
import feature.common.preview.method.MethodReturnsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.stringResource
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_in_email_verify_title
import kotlin.reflect.KClass
import kotlin.time.Clock

private val koinContext = MutableStateFlow<KoinApplication?>(null)

abstract class BaseAuthProvider : KoinFeatureProvider(), FeaturePreviewProvider, AuthFeature {

    private val startGoogleState by lazy { mutableStateOf<Long?>(null) }

    override val type: KClass<out Feature> = AuthFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodReturnsFlow(
            name = "getAuthUser(): Flow<AuthUser?>",
            flow = getAuthUser().map { it?.id.orEmpty() }
        ),
        MethodCallsAction(
            name = "startSignInFlow()",
            action = { startSignInFlow("Sign In") }
        ),
        MethodCallsAction(
            name = "startSignOutFlow()",
            action = ::startSignOutFlow
        ),
        MethodCallsAction(
            name = "startSignInWithEmailFlow()",
            action = ::startSignInWithEmailFlow
        ),
        MethodCallsAction(
            name = "startSignInWithGoogleFlow()",
            action = ::startSignInWithGoogleFlow
        )
    )

    override fun getAuthUser(): Flow<AuthUser?> {
        val useCase = koinApp.koin.get<GetAuthUseCase>()
        return useCase.invoke()
    }

    override fun startSignInFlow(title: String?) {
        onSendAction(StartSignInFlow(title))
    }

    override fun startSignOutFlow() {
        onSendAction(StartSignOutFlow)
    }

    override fun startSignInWithEmailFlow() {
        onSendAction(StartSignInWithEmailFlow)
    }

    override fun startSignInWithGoogleFlow() {
        onSendAction(StartSignInWithGoogleFlow)
    }

    override fun Module.onProvideDI() {
        // common
        singleOf(::createGoogleFlowProvider)
        singleOf(::createAuthRepository)
        factoryOf(::GetAuthUseCase)
        // otp
        viewModelOf(::OtpViewModel)
        // sign in -> email
        viewModelOf(::SignInWithEmailViewModel)
        factoryOf(::SignInWithEmailUseCase)
        factoryOf(::VerifyEmailUseCase)
        // sign in -> google
        factoryOf(::SignInWithGoogleUseCase)
        viewModelOf(::SignInWithGoogleViewModel)
        // sign out
        viewModelOf(::SignOutViewModel)
        factoryOf(::SignOutUseCase)
        // user flow
        viewModelOf(::BasicAuthViewModel)
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureContext) {
        koinContext.value = koinApp
        when (action) {
            is StartSignOutFlow -> context.addBackStack(SignOutRoute)
            is StartSignInFlow -> context.addBackStack(BasicAuthRoute(action.title))
            is StartSignInWithEmailFlow -> context.addBackStack(SignInWithEmailRoute.Start)
            is StartSignInWithGoogleFlow -> startGoogleState.value = Clock.System.now().epochSeconds
        }
    }

    @Composable
    final override fun onProvideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        monitorGoogleAuth()
        content()
    }

    override fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) = builder.run {
        // sign in -> email
        composable<SignInWithEmailRoute.Start> {
            withDI(koinContext.value) {
                SignInWithEmailScreen(
                    onVerify = { email ->
                        val route = SignInWithEmailRoute.Verify(email)
                        context.replaceBackStack(route)
                    },
                    onBack = context::popBackStack,
                )
            }
        }
        composable<SignInWithEmailRoute.Verify> {
            withDI(koinContext.value) {
                val route = it.toRoute<SignInWithEmailRoute.Verify>()
                OtpScreen(
                    title = stringResource(Res.string.auth_sign_in_email_verify_title),
                    subTitle = route.email,
                    onBack = context::popBackStack,
                    onSuccess = context::popBackStack,
                    onVerify = { otp ->
                        val verifyUseCase = koinApp.koin.get<VerifyEmailUseCase>()
                        verifyUseCase.invoke(route.email, otp)
                    },
                    onResend = {
                        val resendUseCase = koinApp.koin.get<SignInWithEmailUseCase>()
                        resendUseCase.invoke(route.email)
                    }
                )
            }
        }

        // sign out
        dialog<SignOutRoute> {
            withDI(koinContext.value) {
                SignOutDialog(
                    onCancel = context::popBackStack,
                    onSuccess = context::popBackStack
                )
            }
        }

        // user flow
        composable<BasicAuthRoute> {
            withDI(koinContext.value) {
                val route = it.toRoute<BasicAuthRoute>()
                BasicAuthScreen(
                    title = route.title,
                    onBack = context::popBackStack,
                    onSignOut = { context.addBackStack(SignOutRoute) },
                    onSignInWithEmail = { context.addBackStack(SignInWithEmailRoute.Start) }
                )
            }
        }
    }

    @Composable
    private fun monitorGoogleAuth() {
        val googleFlowProvider = koinApp.koin.get<GoogleFlowProvider>()
        val googleFlow = googleFlowProvider.provide(rememberCoroutineScope())

        LaunchedEffect(googleFlow) {
            snapshotFlow { startGoogleState.value }
                .filterNotNull()
                .collect {
                    googleFlow.start()
                }
        }
    }

    abstract fun createAuthRepository(): AuthRepository
    abstract fun createGoogleFlowProvider(): GoogleFlowProvider

    protected object StartSignOutFlow : Action
    protected object StartSignInWithEmailFlow : Action
    protected object StartSignInWithGoogleFlow : Action
    protected data class StartSignInFlow(val title: String?) : Action
}