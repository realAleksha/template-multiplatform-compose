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
            implementation(compose.foundation)
            implementation(compose.components.resources)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(projects.feature.common.client.preview)
            implementation(projects.feature.payments.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
        androidMain.dependencies {
            implementation(libs.purchases.core)
        }
        iosMain.dependencies {
            implementation(libs.purchases.core)
        }
    }
}

android {
    namespace = "feature.payments.revenuecat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}