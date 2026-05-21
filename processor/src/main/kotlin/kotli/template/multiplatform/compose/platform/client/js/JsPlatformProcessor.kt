package kotli.template.multiplatform.compose.platform.client.js

import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.model.FeatureTags
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.platform.PlatformProcessor
import kotli.template.multiplatform.compose.platform.client.WebProcessor
import kotlin.reflect.KClass

object JsPlatformProcessor : PlatformProcessor() {

    const val ID = "platform.js"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = listOf(FeatureTags.Client, FeatureTags.Web)

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        WebProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            Rules.JsSrcDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("js { browser() }", ignoreCase = false)
        )
    }

}