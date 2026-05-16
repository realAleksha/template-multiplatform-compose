package kotli.template.multiplatform.compose.data.database.sqldelight

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.RenamePackage
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotli.template.multiplatform.compose.common.CommonStatelyProcessor
import kotli.template.multiplatform.compose.data.database.DatabaseCommonProcessor
import kotli.template.multiplatform.compose.data.database.SqliteProcessor
import kotli.template.multiplatform.compose.platform.client.MobileAndDesktopProcessor
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.hours

object SqlDelightProcessor : BaseFeatureProcessor() {

    const val ID = "data.database.sqldelight"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getWebUrl(state: TemplateState): String = "https://cashapp.github.io/sqldelight/"
    override fun getIntegrationUrl(state: TemplateState): String =
        "https://cashapp.github.io/sqldelight/2.0.2/multiplatform_sqlite/"

    override fun getIntegrationEstimate(state: TemplateState): Long = 4.hours.inWholeMilliseconds

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        MobileAndDesktopProcessor::class,
        DatabaseCommonProcessor::class,
        CommonStatelyProcessor::class,
        SqliteProcessor::class
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            Rules.ClientBuildGradle,
            CleanupMarkedBlock("{sqldelight.config}"),
            CleanupMarkedLine("{data.database.sqldelight}")
        )
        state.onApplyRules(
            "${Rules.ClientCommonMain}/sqldelight",
            RenamePackage(
                "kotli",
                state.layer.namespace
            )
        )
        state.onApplyRules(
            Rules.ClientPlatformConfigKt,
            CleanupMarkedBlock("{data.database.sqldelight}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.ClientBuildGradle,
            RemoveMarkedBlock("{sqldelight.config}"),
            RemoveMarkedLine("{data.database.sqldelight}")
        )
        state.onApplyRules(
            Rules.ClientSqlDelightConfigJs,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.SqlDelightDir,
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("sqldelight"),
                RemoveMarkedLine("stately-common"),
                RemoveMarkedLine("stately-isolate"),
                RemoveMarkedLine("stately-iso-collections"),
            )
        )
        state.onApplyRules(
            Rules.SqlDelightSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("SqlDelightSource"),
            RemoveMarkedLine("DatabaseSource")
        )
        state.onApplyRules(
            Rules.ClientPlatformConfigKt,
            RemoveMarkedBlock("{data.database.sqldelight}"),
            RemoveMarkedLine("sql"),
        )
    }
}