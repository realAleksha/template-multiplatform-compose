package kotli.template.multiplatform.compose.data.common

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules

object CommonDataProcessor : BaseFeatureProcessor() {

    const val ID = "data.common"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.DataDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data")
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.common"),
            RemoveMarkedLine("shared.data"),
            RemoveMarkedLine("shared:data")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("kotlin-test =")
            )
        )
        state.onApplyRules(
            Rules.ClientApplicationKt,
            RemoveMarkedLine("AppHolder")
        )
    }

}