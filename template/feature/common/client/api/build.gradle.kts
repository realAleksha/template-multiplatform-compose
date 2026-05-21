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
                optIn("kotlinx.serialization.InternalSerializationApi")
            }
        }
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(projects.shared.presentation)
        }
    }
}

// {platform.android.config}
android {
    namespace = "feature.common.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}