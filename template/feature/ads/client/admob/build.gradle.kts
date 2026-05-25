@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library) // {platform.android}
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    js { browser() }
    wasmJs { browser() }
    jvm()

    sourceSets {
        all {
            languageSettings {
                optIn("app.lexilabs.basic.ads.DependsOnGoogleMobileAds")
            }
        }
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(projects.feature.ads.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.lexilabs.basic.ads)
            implementation(libs.play.services.ads)
            implementation(libs.user.messaging.platform)
        }
        // {platform.android.dependencies}
        // {platform.ios.dependencies}
        iosMain.dependencies {
            implementation(libs.lexilabs.basic.ads)
        }
        // {platform.ios.dependencies}
    }
}

// {platform.android.config}
android {
    namespace = "feature.ads.admob"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}
