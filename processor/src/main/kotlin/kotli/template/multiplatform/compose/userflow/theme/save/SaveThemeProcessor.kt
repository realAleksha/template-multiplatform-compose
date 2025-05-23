package kotli.template.multiplatform.compose.userflow.theme.save

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.Tags
import kotli.template.multiplatform.compose.dataflow.settings.datastore.DataStoreProcessor
import kotli.template.multiplatform.compose.dataflow.settings.multiplatform.MultiplatformSettingsProcessor
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.hours

object SaveThemeProcessor : BaseFeatureProcessor() {

    const val ID = "userflow.theme.save"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = Tags.AllClients
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds
    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        MultiplatformSettingsProcessor::class,
        DataStoreProcessor::class,
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "${Rules.AppTheme}/provide/presentation/ThemeStatelessViewModel.kt",
            RemoveFile()
        )
        state.onApplyRules(
            Rules.AppThemeConfigKt,
            RemoveMarkedLine("ThemeStatelessViewModel")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "${Rules.AppTheme}/provide/presentation/ThemeStatefulViewModel.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "${Rules.AppTheme}/provide/domain",
            RemoveFile()
        )
        state.onApplyRules(
            "${Rules.AppTheme}/provide/data",
            RemoveFile()
        )
        state.onApplyRules(
            Rules.AppThemeConfigKt,
            RemoveMarkedLine("UseCase"),
            RemoveMarkedLine("Repository"),
            RemoveMarkedLine("ThemeStatefulViewModel"),
        )
        state.onApplyRules(
            "${Rules.AppTheme}/provide/presentation/ThemeProvider.kt",
            ReplaceMarkedText(
                "ThemeStatefulViewModel",
                "ThemeStatefulViewModel",
                "ThemeStatelessViewModel"
            )
        )
    }

}