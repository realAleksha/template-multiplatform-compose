package kotli.template.multiplatform.compose.data.expression.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotli.template.multiplatform.compose.common.CommonStatelyCollectionsProcessor
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.hours

object BasicExpressionProcessor : BaseFeatureProcessor() {

    const val ID = "data.expression.basic"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds
    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        CommonStatelyCollectionsProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.ExpressionDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ExpressionImplDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ExpressionSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.DataBuildGradle,
            RemoveMarkedLine("multiplatform.expressions.evaluator")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("multiplatform-expressions-evaluator")
            )
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data:expression"),
            RemoveMarkedLine("shared:data:expression-impl")
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.expression"),
            RemoveMarkedLine("projects.shared.data.expressionImpl")
        )
    }

}