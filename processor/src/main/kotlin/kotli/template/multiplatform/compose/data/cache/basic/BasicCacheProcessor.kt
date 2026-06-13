package kotli.template.multiplatform.compose.data.cache.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotli.template.multiplatform.compose.common.CommonStatelyCollectionsProcessor
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.hours

object BasicCacheProcessor : BaseFeatureProcessor() {

    const val ID = "data.cache.basic"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getIntegrationEstimate(state: TemplateState): Long = 8.hours.inWholeMilliseconds
    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        CommonStatelyCollectionsProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.CacheSourceDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.CacheSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("CacheSource")
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data:cache")
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.cache")
        )
    }

}