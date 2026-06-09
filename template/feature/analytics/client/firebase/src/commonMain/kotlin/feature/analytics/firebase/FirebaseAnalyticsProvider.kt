package feature.analytics.firebase

import androidx.compose.runtime.Composable
import feature.analytics.api.AnalyticsFeature
import feature.common.api.BaseFeatureProvider
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import kotlin.reflect.KClass

class FirebaseAnalyticsProvider : BaseFeatureProvider(), FeaturePreview, AnalyticsFeature {

    override val name: String = "Firebase Analytics"

    override val type: KClass<out Feature> = AnalyticsFeature::class

    override fun isAvailable(): Boolean = isSupported()

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("logEvent('test_event')") {
            logEvent("test_event")
        }
    )

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        content()
    }

    override fun logEvent(event: String, params: Map<String, String>) {
        logFirebaseEvent(event, params)
    }

    override fun setUserProperty(name: String, value: String) {
        setFirebaseUserProperty(name, value)
    }

    override fun setUserId(userId: String?) {
        setFirebaseUserId(userId)
    }
}
