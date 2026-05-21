package kotli.template.multiplatform.compose.platform.client.android

import kotli.engine.FeatureTag
import kotli.engine.TemplateState
import kotli.engine.model.FeatureTags
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.multiplatform.compose.Rules
import kotli.template.multiplatform.compose.platform.PlatformProcessor

object AndroidPlatformProcessor : PlatformProcessor() {

    const val ID = "platform.android"

    override fun getId(): String = ID
    override fun getTags(): List<FeatureTag> = listOf(FeatureTags.Client, FeatureTags.Android)

    override fun doRemove(state: TemplateState) {
        super.doRemove(state)
        state.onApplyRules(
            Rules.AndroidSrcDir,
            RemoveFile()
        )
        state.onApplyRules(
            "${Rules.ClientAssembleDir}/android",
            RemoveFile()
        )
        state.onApplyRules(
            Rules.RootBuildGradle,
            RemoveMarkedLine("android")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("agp"),
                RemoveMarkedLine("android-jvmTarget"),
                RemoveMarkedLine("android-compileSdk"),
                RemoveMarkedLine("android-minSdk"),
                RemoveMarkedLine("android-targetSdk"),
                RemoveMarkedLine("androidx-activity"),
                RemoveMarkedLine("androidx-appcompat"),
                RemoveMarkedLine("androidx-splashscreen"),
                RemoveMarkedLine("compose-android"),
                RemoveMarkedLine("coroutines-android"),
                RemoveMarkedLine("client-android")
            )
        )
        state.onApplyRules(
            Rules.BuildGradle,
            RemoveMarkedLine("androidTarget()"),
        )
    }
}