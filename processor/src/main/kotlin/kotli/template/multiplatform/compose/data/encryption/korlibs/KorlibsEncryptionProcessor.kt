package kotli.template.multiplatform.compose.data.encryption.korlibs

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotlin.time.Duration.Companion.hours

object KorlibsEncryptionProcessor : BaseFeatureProcessor() {

    const val ID = "data.encryption.korlibs"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getWebUrl(state: TemplateState): String = "https://github.com/korlibs/korlibs"
    override fun getIntegrationUrl(state: TemplateState): String =
        "https://github.com/korlibs/korlibs"

    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            Rules.EncryptionDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.EncryptionKorlibsDir,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.EncryptionSource,
            RemoveFile()
        )
        state.onApplyRules(
            Rules.ClientCommonConfigKt,
            RemoveMarkedLine("EncryptionSource")
        )
        state.onApplyRules(
            Rules.DataBuildGradle,
            RemoveMarkedLine("korlibs")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("korlibs")
            )
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            RemoveMarkedLine("shared:data:encryption"),
            RemoveMarkedLine("shared:data:encryption-korlibs")
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("projects.shared.data.encryption"),
            RemoveMarkedLine("projects.shared.data.encryptionKorlibs")
        )
    }

}