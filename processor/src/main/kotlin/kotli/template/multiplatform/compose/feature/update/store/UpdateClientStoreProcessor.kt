package kotli.template.multiplatform.compose.feature.update.store

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.update.UpdateClientApiProcessor
import kotlin.reflect.KClass

object UpdateClientStoreProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:update:client:store"

    override val featureName: String = "StoreUpdate"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        UpdateClientApiProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("google-play-app-update")
            )
        )
    }
}
