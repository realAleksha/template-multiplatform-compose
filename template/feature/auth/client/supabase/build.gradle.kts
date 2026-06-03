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
            implementation(libs.supabase.auth)
            implementation(libs.supabase.compose.auth)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(projects.feature.auth.client.api)
            implementation(projects.feature.auth.client.base)
        }
    }
}

// {platform.android.config}
android {
    namespace = "feature.auth.supabase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}