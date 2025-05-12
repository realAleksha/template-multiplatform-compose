package feature.passcode.basic

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import feature.common.BaseFeatureProvider
import feature.common.FeatureContext
import feature.passcode.api.PasscodeFeature
import feature.passcode.basic.data.PasscodeRepositoryImpl
import feature.passcode.basic.domain.repository.PasscodeRepository
import feature.passcode.basic.domain.usecase.CheckPasscodeUseCase
import feature.passcode.basic.domain.usecase.ForgotPasscodeUseCase
import feature.passcode.basic.domain.usecase.GetLockStateUseCase
import feature.passcode.basic.domain.usecase.GetPasscodeLengthUseCase
import feature.passcode.basic.domain.usecase.GetRemainingAttemptsUseCase
import feature.passcode.basic.domain.usecase.IsPasscodeSetUseCase
import feature.passcode.basic.domain.usecase.ResetPasscodeUseCase
import feature.passcode.basic.domain.usecase.SetPasscodeUseCase
import feature.passcode.basic.domain.usecase.UnlockPasscodeUseCase
import feature.passcode.basic.domain.usecase.UpdatePasscodeUseCase
import feature.passcode.basic.presentation.forgot.ForgotPasscodeViewModel
import feature.passcode.basic.presentation.provide.PasscodeProvider
import feature.passcode.basic.presentation.provide.PasscodeViewModel
import feature.passcode.basic.presentation.reset.ResetPasscodeRoute
import feature.passcode.basic.presentation.reset.ResetPasscodeScreen
import feature.passcode.basic.presentation.reset.ResetPasscodeViewModel
import feature.passcode.basic.presentation.set.SetPasscodeRoute
import feature.passcode.basic.presentation.set.SetPasscodeScreen
import feature.passcode.basic.presentation.set.SetPasscodeViewModel
import feature.passcode.basic.presentation.unlock.UnlockPasscodeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import shared.data.source.encryption.EncryptionMethod
import shared.data.source.encryption.EncryptionSource
import shared.data.source.settings.SettingsSource
import kotlin.time.Duration.Companion.seconds

class BasicPasscodeProvider(
    private val settingsSource: SettingsSource,
    private val encryptionSource: EncryptionSource,
    private val passcodeLength: Int = 4,
    private val unlockAttemptsCount: Int = 5,
    private val persistentKey: String = "passcode_config",
    private val resumeTimeout: Long = 10.seconds.inWholeMilliseconds,
    private val encryptionMethod: (code: String) -> EncryptionMethod = EncryptionMethod::PBKDF2,
) : BaseFeatureProvider(), PasscodeFeature {

    override fun Module.provideDI() {
        single<PasscodeRepository> {
            PasscodeRepositoryImpl(
                settingsSource = settingsSource,
                encryptionSource = encryptionSource,
                passcodeLength = passcodeLength,
                unlockAttemptsCount = unlockAttemptsCount,
                persistentKey = persistentKey,
                resumeTimeout = resumeTimeout,
                encryptionMethod = encryptionMethod
            )
        }

        factoryOf(::ForgotPasscodeUseCase)
        factoryOf(::GetPasscodeLengthUseCase)
        factoryOf(::GetLockStateUseCase)
        factoryOf(::GetRemainingAttemptsUseCase)
        factoryOf(::IsPasscodeSetUseCase)
        factoryOf(::ResetPasscodeUseCase)
        factoryOf(::SetPasscodeUseCase)
        factoryOf(::UnlockPasscodeUseCase)
        factoryOf(::UpdatePasscodeUseCase)
        factoryOf(::CheckPasscodeUseCase)

        viewModelOf(::ForgotPasscodeViewModel)
        viewModelOf(::PasscodeViewModel)
        viewModelOf(::ResetPasscodeViewModel)
        viewModelOf(::SetPasscodeViewModel)
        viewModelOf(::UnlockPasscodeViewModel)
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureContext) {
        when (action) {
            SetPasscode -> context.addBackStack(SetPasscodeRoute)
            ResetPasscode -> context.addBackStack(ResetPasscodeRoute)
        }
    }

    @Composable
    override fun provideUI(context: FeatureContext, content: @Composable (() -> Unit)) {
        withDI {
            PasscodeProvider(content)
        }
    }

    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) = builder.run {
        composable<SetPasscodeRoute> {
            withDI {
                SetPasscodeScreen(context::popBackStack)
            }
        }

        composable<ResetPasscodeRoute> {
            withDI {
                ResetPasscodeScreen(context::popBackStack)
            }
        }
    }

    override fun setPasscode() {
        onSendAction(SetPasscode)
    }

    override fun resetPasscode() {
        onSendAction(ResetPasscode)
    }

    private object SetPasscode : Action
    private object ResetPasscode : Action
}