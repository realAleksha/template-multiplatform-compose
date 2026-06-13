package kotli.template.multiplatform.compose.data.http.ktor

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotli.template.multiplatform.compose.common.CommonKtorProcessor
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.hours

object KtorHttpProcessor : BaseFeatureProcessor() {

    const val ID = "data.http.ktor"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getWebUrl(state: TemplateState): String = "https://ktor.io"
    override fun getIntegrationUrl(state: TemplateState): String =
        "https://ktor.io/docs/client-create-new-application.html"

    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds
    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        CommonKtorProcessor::class
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.HttpDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.HttpSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("HttpSource")
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data:http")
        )
        state.onApplyRules(
            Rules.DataBuildGradle,
            RemoveMarkedLine("ktor")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("ktor-client")
            )
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.http")
        )
    }

}