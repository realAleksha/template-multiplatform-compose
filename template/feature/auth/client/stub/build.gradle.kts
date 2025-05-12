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
        commonMain.dependencies {
            implementation(projects.feature.common.client)
            implementation(projects.feature.auth.client.api)
            implementation(projects.feature.auth.client.base)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
    }
}

android {
    namespace = "feature.auth.stub"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}