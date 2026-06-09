package kotli.template.multiplatform.compose.feature.analytics

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProvider
import kotli.template.multiplatform.compose.feature.analytics.firebase.AnalyticsClientFirebaseProcessor
import kotli.template.multiplatform.compose.feature.analytics.stub.AnalyticsClientStubProcessor

object AnalyticsFeatureProvider : UserFeatureProvider() {

    override fun getId(): String = "feature.analytics"

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        AnalyticsClientApiProcessor,
        AnalyticsClientStubProcessor,
        AnalyticsClientFirebaseProcessor
    )
}