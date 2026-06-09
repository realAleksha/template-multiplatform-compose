package kotli.template.multiplatform.compose.feature.analytics.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.analytics.AnalyticsClientApiProcessor
import kotlin.reflect.KClass

object AnalyticsClientFirebaseProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:analytics:client:firebase"

    override val featureName: String = "FirebaseAnalyticsProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        AnalyticsClientApiProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("firebase-kotlin-sdk")
            )
        )
    }
}
