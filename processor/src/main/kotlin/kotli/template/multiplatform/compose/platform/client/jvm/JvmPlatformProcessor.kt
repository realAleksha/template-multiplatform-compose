package kotli.template.multiplatform.compose.platform.client.jvm

import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.model.FeatureTags
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.platform.PlatformProcessor

object JvmPlatformProcessor : PlatformProcessor() {

    const val ID = "platform.jvm"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = listOf(FeatureTags.Client, FeatureTags.Desktop)

    override fun doApply(state: TemplateState) {
        super.doApply(state)
        state.onApplyRules(
            Rules.RootSettingsGradle,
            CleanupMarkedBlock(configBlock)
        )
    }

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            Rules.JvmSrcDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.RootBuildGradle,
            RemoveMarkedLine("hot.reload")
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("firework/dev"),
            RemoveMarkedBlock(configBlock)
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("coroutines-swing"),
                RemoveMarkedLine("client-java"),
                RemoveMarkedLine("slf4j-simple")
            )
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("jvm()")
        )
    }
}