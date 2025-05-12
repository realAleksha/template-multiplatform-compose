package feature.auth.base

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import feature.auth.api.AuthFeature
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
import feature.common.BaseFeatureProvider
import feature.common.FeatureContext
import org.jetbrains.compose.resources.stringResource
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import template.feature.auth.client.base.generated.resources.Res
import template.feature.auth.client.base.generated.resources.auth_sign_in_email_verify_title

abstract class BaseAuthProvider : BaseFeatureProvider(), AuthFeature {

    override fun Module.provideDI() {
        // common
        withAuthRepository()
        withGoogleFlowProvider()
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
        // userflow
        viewModelOf(::BasicAuthViewModel)
    }

    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) = builder.run {
        // sign in -> email
        composable<SignInWithEmailRoute.Start> {
            withDI {
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
            withDI {
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
            withDI {
                SignOutDialog(
                    onCancel = context::popBackStack,
                    onSuccess = context::popBackStack
                )
            }
        }

        // userflow
        composable<BasicAuthRoute> {
            withDI {
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

    abstract fun Module.withGoogleFlowProvider(): KoinDefinition<GoogleFlowProvider>

    abstract fun Module.withAuthRepository(): KoinDefinition<AuthRepository>
}