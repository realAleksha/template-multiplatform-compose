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
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(projects.feature.analytics.client.api)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.firebase.analytics)
        }
        // {platform.android.dependencies}
        // {platform.ios.dependencies}
        iosMain.dependencies {
            implementation(libs.firebase.analytics)
        }
        // {platform.ios.dependencies}
        // {platform.js.dependencies}
        jsMain.dependencies {
            implementation(libs.firebase.analytics)
        }
        // {platform.js.dependencies}
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            implementation(libs.firebase.analytics)
        }
        // {platform.jvm.dependencies}
    }
}

// {platform.android.config}
android {
    namespace = "feature.analytics.firebase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}
