package kotli.template.multiplatform.compose.data.paging.jetpack

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotlin.time.Duration.Companion.hours

object JetpackPagingProcessor : BaseFeatureProcessor() {

    const val ID = "data.paging.jetpack"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getWebUrl(state: TemplateState): String =
        "https://developer.android.com/topic/libraries/architecture/paging/v3-overview"

    override fun getIntegrationUrl(state: TemplateState): String =
        "https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data"

    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidx-paging")
            )
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("JetpackPagingSource")
        )
        state.onApplyRules(
            Rules.ClientBuildGradle,
            CleanupMarkedBlock("androidx.paging"),
        )
        state.onApplyRules(
            Rules.PresentationBuildGradle,
            RemoveMarkedLine("androidx.paging")
        )
        state.onApplyRules(
            Rules.DataBuildGradle,
            RemoveMarkedLine("androidx.paging")
        )
        state.onApplyRules(
            "*/paging/jetpack/*",
            RemoveFile()
        )
    }

}