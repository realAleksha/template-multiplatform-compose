package kotli.template.multiplatform.compose

import kotli.engine.BaseTemplateProcessor
import kotli.engine.FeatureProvider
import kotli.engine.LayerType
import kotli.engine.TemplateState
import kotli.engine.model.Feature
import kotli.engine.model.Layer
import kotli.engine.model.LayerTypes
import kotli.engine.template.rule.RenamePackage
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.multiplatform.compose.common.CommonProvider
import kotli.template.multiplatform.compose.data.analytics.AnalyticsProvider
import kotli.template.multiplatform.compose.data.cache.CacheProvider
import kotli.template.multiplatform.compose.data.common.CommonDataProvider
import kotli.template.multiplatform.compose.data.config.ConfigProvider
import kotli.template.multiplatform.compose.data.database.DatabaseProvider
import kotli.template.multiplatform.compose.data.encryption.EncryptionProvider
import kotli.template.multiplatform.compose.data.expression.ExpressionProvider
import kotli.template.multiplatform.compose.data.http.HttpProvider
import kotli.template.multiplatform.compose.data.paging.PagingProvider
import kotli.template.multiplatform.compose.data.settings.SettingsProvider
import kotli.template.multiplatform.compose.dev.debugging.DebuggingProvider
import kotli.template.multiplatform.compose.dev.logging.LoggingProvider
import kotli.template.multiplatform.compose.feature.ads.AdsFeatureProvider
import kotli.template.multiplatform.compose.feature.analytics.AnalyticsFeatureProvider
import kotli.template.multiplatform.compose.feature.auth.AuthFeatureProvider
import kotli.template.multiplatform.compose.feature.common.CommonFeatureProvider
import kotli.template.multiplatform.compose.feature.loader.LoaderFeatureProvider
import kotli.template.multiplatform.compose.feature.navigation.NavigationFeatureProvider
import kotli.template.multiplatform.compose.feature.onboarding.OnboardingFeatureProvider
import kotli.template.multiplatform.compose.feature.passcode.PasscodeFeatureProvider
import kotli.template.multiplatform.compose.feature.payments.PaymentsFeatureProvider
import kotli.template.multiplatform.compose.feature.profile.ProfileFeatureProvider
import kotli.template.multiplatform.compose.feature.review.ReviewFeatureProvider
import kotli.template.multiplatform.compose.feature.settings.SettingsFeatureProvider
import kotli.template.multiplatform.compose.feature.splash.SplashFeatureProvider
import kotli.template.multiplatform.compose.feature.support.SupportFeatureProvider
import kotli.template.multiplatform.compose.feature.theme.ThemeFeatureProvider
import kotli.template.multiplatform.compose.feature.update.UpdateFeatureProvider
import kotli.template.multiplatform.compose.foundation.buildtool.BuildToolProvider
import kotli.template.multiplatform.compose.foundation.design.DesignSystemProvider
import kotli.template.multiplatform.compose.foundation.di.DIProvider
import kotli.template.multiplatform.compose.foundation.navigation.NavigationProvider
import kotli.template.multiplatform.compose.foundation.uikit.UiKitProvider
import kotli.template.multiplatform.compose.platform.client.ClientPlatformProvider
import kotli.template.multiplatform.compose.platform.client.android.AndroidPlatformProcessor
import kotli.template.multiplatform.compose.platform.client.ios.IOSPlatformProcessor
import kotli.template.multiplatform.compose.platform.client.jvm.JvmPlatformProcessor
import kotli.template.multiplatform.compose.platform.server.ServerPlatformProvider
import kotli.template.multiplatform.compose.platform.shared.SharedPlatformProvider
import kotli.template.multiplatform.compose.ui.component.file.FileProvider
import kotli.template.multiplatform.compose.ui.component.image.ImageProvider
import kotli.template.multiplatform.compose.ui.component.text.TextProvider

object MultiplatformComposeTemplateProcessor : BaseTemplateProcessor() {

    const val ID = "template-multiplatform-compose"

    override fun getId(): String = ID
    override fun getType(): LayerType = LayerTypes.Multiplatform
    override fun getWebUrl(): String =
        "https://github.com/realAleksha/template-multiplatform-compose"

    override fun createPresets(): List<Layer> = listOf(
        createPreset(
            features = listOf(
                Feature(IOSPlatformProcessor.ID),
                Feature(AndroidPlatformProcessor.ID),
                Feature(JvmPlatformProcessor.ID)
            )
        )
    )

    override fun createProviders(): List<FeatureProvider> = listOf(
        // Common
        CommonProvider,

        // Foundation
        DIProvider,
        UiKitProvider,
        NavigationProvider,
        DesignSystemProvider,
        BuildToolProvider,

        // Platforms
        ClientPlatformProvider,
        ServerPlatformProvider,
        SharedPlatformProvider,

        // Dev Tools
        DebuggingProvider,
        LoggingProvider,

        // Data Layer
        CommonDataProvider,
        SettingsProvider,
        EncryptionProvider,
        ExpressionProvider,
        CacheProvider,
        ConfigProvider,
        DatabaseProvider,
        HttpProvider,
        PagingProvider,
        AnalyticsProvider,

        // UI Layer
        TextProvider,
        ImageProvider,
        FileProvider,

        // User Features
        CommonFeatureProvider,
        SplashFeatureProvider,
        ThemeFeatureProvider,
        PasscodeFeatureProvider,
        NavigationFeatureProvider,
        AuthFeatureProvider,
        AdsFeatureProvider,
        AnalyticsFeatureProvider,
        LoaderFeatureProvider,
        OnboardingFeatureProvider,
        PaymentsFeatureProvider,
        ProfileFeatureProvider,
        ReviewFeatureProvider,
        SettingsFeatureProvider,
        SupportFeatureProvider,
        UpdateFeatureProvider
    )

    override fun processBefore(state: TemplateState) {
        state.onApplyRules(
            Rules.IndexHtml,
            ReplaceMarkedText(
                text = "Template",
                marker = "title",
                replacer = state.layer.name,
                singleLine = true
            )
        )
        state.onApplyRules(
            Rules.StringsXml,
            ReplaceMarkedText(
                text = "Template",
                marker = "app_name",
                replacer = state.layer.name,
                singleLine = true
            )
        )
        state.onApplyRules(
            Rules.IosConfig,
            ReplaceMarkedText(
                text = "kotli",
                marker = "BUNDLE_ID",
                replacer = state.layer.namespace,
                singleLine = true
            ),
            ReplaceMarkedText(
                text = "Kotli",
                marker = "APP_NAME",
                replacer = state.layer.name,
                singleLine = true
            )
        )
        state.onApplyRules(
            Rules.ClientBuildGradle,
            ReplaceMarkedText(
                text = "kotli",
                marker = "{kotli.namespace}",
                replacer = state.layer.namespace
            )
        )
        state.onApplyRules(
            Rules.ClientBuildGradle,
            ReplaceMarkedText(
                text = "kotli",
                marker = "{kotli.name}",
                replacer = state.layer.name
            )
        )
        state.onApplyRules(
            Rules.RootSettingsGradle,
            ReplaceMarkedText(
                text = "template",
                marker = "rootProject.name",
                replacer = normalizeRootName(state.layer.name),
                singleLine = true
            )
        )
        state.onApplyRules(
            Rules.ClientProguardRulesPro,
            ReplaceMarkedText(
                text = "kotli",
                marker = "kotli.",
                replacer = state.layer.namespace
            )
        )
    }

    override fun processAfter(state: TemplateState) {
        val name = normalizeRootName(state.layer.name)
        state.onApplyRules(
            "*.kt",
            ReplaceMarkedText(
                text = "import template.",
                marker = "import template.",
                replacer = "import ${name}."
            )
        )
        state.onApplyRules(
            "${Rules.ServerSrc}/*.yaml",
            ReplaceMarkedText(
                text = "kotli",
                marker = "kotli",
                replacer = state.layer.namespace
            )
        )
        renamePackage(state, "${Rules.ClientSrc}/androidMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/commonMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/iosArm64Main/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/iosMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/iosSimulatorArm64Main/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/jsMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/wasmJsMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/jvmMain/kotlin")
        renamePackage(state, "${Rules.ClientSrc}/mobileAndDesktopMain/kotlin")
        renamePackage(state, "${Rules.ServerSrc}/main/kotlin")
        renamePackage(state, "${Rules.ServerSrc}/test/kotlin")
    }

    private fun renamePackage(state: TemplateState, root: String) {
        state.onApplyRules(
            root,
            RenamePackage(
                "kotli",
                state.layer.namespace
            )
        )
    }

    private fun normalizeRootName(name: String): String {
        return name.lowercase()
            .replace("_", "")
            .replace("-", "")
            .replace(".", "")
    }

}
