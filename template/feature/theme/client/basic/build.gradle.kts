plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js { browser() }
    jvm()

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(projects.shared.data)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client)
            implementation(projects.feature.theme.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
    }
}

android {
    namespace = "feature.theme.basic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}