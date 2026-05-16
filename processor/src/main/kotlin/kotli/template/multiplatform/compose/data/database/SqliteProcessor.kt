package kotli.template.multiplatform.compose.data.database

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotlin.reflect.KClass

object SqliteProcessor : BaseFeatureProcessor() {

    const val ID = "data.database.sqlite"
    override fun isInternal(): Boolean = true
    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        SqliteLinkerProcessor::class
    )

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            Rules.ClientBuildGradle,
            CleanupMarkedLine("{data.database.sqlite}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.ClientBuildGradle,
            RemoveMarkedLine("{data.database.sqlite}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidx-sqlite")
            )
        )
    }

}