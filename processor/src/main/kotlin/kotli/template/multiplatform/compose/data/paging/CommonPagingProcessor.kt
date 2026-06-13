package kotli.template.multiplatform.compose.data.paging

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules

object CommonPagingProcessor : BaseFeatureProcessor() {

    const val ID = "data.paging.common"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.PagingSourceDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.PagingSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("PagingSource")
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data:paging")
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.paging")
        )
    }

}