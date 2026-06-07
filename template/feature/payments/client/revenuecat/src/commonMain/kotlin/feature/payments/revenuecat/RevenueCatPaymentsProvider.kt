package feature.payments.revenuecat

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.koin.KoinActionFeatureProvider
import feature.payments.api.PaymentsFeature
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

class RevenueCatPaymentsProvider(
    private val apiKey: String,
    private val apiUserId: String,
) : KoinActionFeatureProvider(), FeaturePreview, PaymentsFeature {

    override val name: String = "RevenueCat Payments"

    override val type: KClass<out Feature> = PaymentsFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("showPaywall()") {
            showPaywall()
        },
        MethodCallsAction("hasEntitlement('premium')") {
            this@RevenueCatPaymentsProvider.hasEntitlement("premium")
        }
    )

    override fun showPaywall(offeringId: String?) {
        onSendAction(ShowPaywall(offeringId))
    }

    override suspend fun hasEntitlement(entitlementId: String): Boolean {
        return feature.payments.revenuecat.hasEntitlement(entitlementId)
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureNavContext) {
        when (action) {
            is ShowPaywall -> context.pushDestination(PaywallRoute(action.offeringId))
        }
    }

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        withDI {
            OnProvideContent(apiKey, apiUserId)
            content()
        }
    }

    override fun onProvideNavigation(context: FeatureNavContext, builder: NavGraphBuilder) {
        builder.run {
            dialog<PaywallRoute> { entry ->
                withDI {
                    val route: PaywallRoute = entry.toRoute()
                    OnPaywallRoute(
                        offeringId = route.offeringId,
                        onDismissRequest = context::popDestination
                    )
                }
            }
        }
    }

    private data class ShowPaywall(val offeringId: String?) : Action
}

@Serializable
internal data class PaywallRoute(val offeringId: String? = null)