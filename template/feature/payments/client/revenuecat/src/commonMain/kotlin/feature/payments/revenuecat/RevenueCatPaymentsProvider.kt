package feature.payments.revenuecat

import androidx.compose.runtime.Composable
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.koin.KoinFeatureProvider
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.payments.api.PaymentsFeature
import kotlin.reflect.KClass

class RevenueCatPaymentsProvider : KoinFeatureProvider(), FeaturePreview, PaymentsFeature {

    override val name: String = "RevenueCat Payments"

    override val type: KClass<out Feature> = PaymentsFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf()

    override fun showPaywall() {
        TODO("Not yet implemented")
    }

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        content()
    }
}