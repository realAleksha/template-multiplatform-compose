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
                optIn("kotlin.time.ExperimentalTime")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(projects.shared.data)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client)
            implementation(projects.feature.passcode.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
    }
}

android {
    namespace = "feature.passcode.basic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}