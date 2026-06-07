package kotli.template.multiplatform.compose.feature.payments.revenuecat

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.payments.PaymentsClientApiProcessor
import kotlin.reflect.KClass

object PaymentsClientRevenueCatProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:payments:client:revenuecat"

    override val featureName: String = "RevenueCatPaymentsProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        PaymentsClientApiProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("purchases-kmp")
            )
        )
    }
}