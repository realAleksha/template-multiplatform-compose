package kotli.template.multiplatform.compose.platform.client

import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.platform.PlatformProcessor

object WebProcessor : PlatformProcessor() {

    const val ID = "platform.web"
    override fun isInternal(): Boolean = true

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.WebSrcDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientWebPackConfigDir,
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("client-js")
            )
        )
        state.onApplyRules(
            Rules.GradleProperties,
            RemoveMarkedLine("js")
        )
    }

}