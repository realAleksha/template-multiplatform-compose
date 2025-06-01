plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
        commonMain.dependencies {
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
        }
    }
}

android {
    namespace = "feature.loader.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}