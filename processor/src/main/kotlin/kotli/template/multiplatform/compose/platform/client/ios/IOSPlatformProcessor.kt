package kotli.template.multiplatform.compose.platform.client.ios

import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.model.FeatureTags
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.platform.PlatformProcessor

object IOSPlatformProcessor : PlatformProcessor() {

    const val ID = "platform.ios"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = listOf(FeatureTags.Client, FeatureTags.IOS)

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            Rules.IosSrcDir,
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("client-darwin")
            )
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("iosArm64()"),
            RemoveMarkedLine("iosSimulatorArm64()")
        )
    }
}