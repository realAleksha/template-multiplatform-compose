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
            implementation(libs.compose.components.resources)
            implementation(projects.shared.data)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(projects.feature.theme.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
    }
}

// {platform.android.config}
android {
    namespace = "feature.theme.basic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}