package feature.analytics.stub

import androidx.compose.runtime.Composable
import feature.analytics.api.AnalyticsFeature
import feature.common.api.BaseFeatureProvider
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import kotlin.reflect.KClass

class StubAnalyticsProvider : BaseFeatureProvider(), FeaturePreview, AnalyticsFeature {

    override val name: String = "Stub Analytics"

    override val type: KClass<out Feature> = AnalyticsFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("logEvent('test_event')") {
            logEvent("test_event")
        }
    )

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        content()
    }

    override fun logEvent(event: String, params: Map<String, String>) {}
    override fun setUserProperty(name: String, value: String) {}
    override fun setUserId(userId: String?) {}
}
