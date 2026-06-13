package feature.passcode.basic

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsFlowAction
import feature.common.api.preview.MethodReturnsSuspendValue
import feature.common.koin.KoinActionFeatureProvider
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
import feature.passcode.basic.presentation.forgot.ForgotPasscodeDialog
import feature.passcode.basic.presentation.forgot.ForgotPasscodeRoute
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
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import shared.data.encryption.EncryptionMethod
import shared.data.encryption.EncryptionSource
import shared.data.settings.SettingsSource
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds

class BasicPasscodeProvider(
    private val settingsSource: SettingsSource,
    private val encryptionSource: EncryptionSource,
    private val passcodeLength: Int = 4,
    private val unlockAttemptsCount: Int = 5,
    private val persistentKey: String = "passcode_config",
    private val resumeTimeout: Long = 60.seconds.inWholeMilliseconds,
    private val encryptionMethod: (code: String) -> EncryptionMethod = EncryptionMethod::PBKDF2,
) : KoinActionFeatureProvider(), FeaturePreview, PasscodeFeature {

    override val name: String = "Basic Passcode"

    override val type: KClass<out Feature> = PasscodeFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodReturnsSuspendValue(
            name = "isPasscodeSet(): Boolean",
            value = { isPasscodeSet().toString() }
        ),
        MethodCallsFlowAction(
            "startSetPasscodeFlow()",
            action = ::startSetPasscodeFlow
        ),
        MethodCallsFlowAction(
            "startResetPasscodeFlow()",
            action = ::startResetPasscodeFlow
        ),
        MethodCallsFlowAction(
            "startForgotPasscodeFlow()",
            action = ::startForgotPasscodeFlow
        ),
    )

    override suspend fun isPasscodeSet(): Boolean {
        val useCase = koinApp.koin.get<IsPasscodeSetUseCase>()
        return useCase.invoke()
    }

    override fun startSetPasscodeFlow() {
        onSendAction(SetPasscode)
    }

    override fun startResetPasscodeFlow() {
        onSendAction(ResetPasscode)
    }

    override fun startForgotPasscodeFlow() {
        onSendAction(ForgotPasscode)
    }

    override fun Module.onProvideDI() {
        single<PasscodeRepository> {
            PasscodeRepositoryImpl(
                settingsSource = get(),
                encryptionSource = get(),
                persistentKey = persistentKey,
                resumeTimeout = resumeTimeout,
                passcodeLength = passcodeLength,
                encryptionMethod = encryptionMethod,
                unlockAttemptsCount = unlockAttemptsCount
            )
        }
        singleOf(::settingsSource)
        singleOf(::encryptionSource)

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

    override suspend fun onReceiveAction(action: Action, context: FeatureNavContext) {
        when (action) {
            SetPasscode -> context.restoreDestination(SetPasscodeRoute)
            ResetPasscode -> context.restoreDestination(ResetPasscodeRoute)
            ForgotPasscode -> context.restoreDestination(ForgotPasscodeRoute)
        }
    }

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        withDI {
            PasscodeProvider(content)
        }
    }

    override fun onProvideNavigation(context: FeatureNavContext, builder: NavGraphBuilder) =
        builder.run {
            composable<SetPasscodeRoute> {
                withDI {
                    SetPasscodeScreen(context::popDestination)
                }
            }

            composable<ResetPasscodeRoute> {
                withDI {
                    ResetPasscodeScreen(context::popDestination)
                }
            }

            dialog<ForgotPasscodeRoute> {
                withDI {
                    ForgotPasscodeDialog(context::popDestination)
                }
            }
        }

    private object SetPasscode : Action
    private object ResetPasscode : Action
    private object ForgotPasscode : Action
}