plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
            implementation(projects.feature.update.client.api)
            implementation(projects.shared.data.common)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.koin)
        }

        androidMain.dependencies {
            implementation(libs.google.play.app.update)
            implementation(libs.google.play.app.update.ktx)
        }
    }
}

// {platform.android.config}
android {
    namespace = "feature.update.store"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
// {platform.android.config}
