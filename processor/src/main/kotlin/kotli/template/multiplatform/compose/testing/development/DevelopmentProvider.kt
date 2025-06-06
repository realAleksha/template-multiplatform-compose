package kotli.template.multiplatform.compose.testing.development

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.multiplatform.compose.testing.development.hotreload.HotReloadProcessor

object DevelopmentProvider : BaseFeatureProvider() {

    override fun getId(): String = "testing.development"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Testing
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        HotReloadProcessor
    )
}