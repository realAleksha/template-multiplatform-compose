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
        commonMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(projects.feature.loader.client.api)
        }
    }
}

// {platform.android.config}
android {
    namespace = "feature.loader.basic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}