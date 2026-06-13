plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library) // {platform.android}
}

kotlin {
    // {platform.android.target}
    androidTarget()
    // {platform.android.target}
    // {platform.ios.target}
    iosArm64()
    iosSimulatorArm64()
    // {platform.ios.target}
    // {platform.js.target}
    js {
        browser()
    }
    // {platform.js.target}
    // {platform.wasmJs.target}
    wasmJs {
        browser()
    }
    // {platform.wasmJs.target}
    // {platform.jvm.target}
    jvm()
    // {platform.jvm.target}
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.data.common)
        }
    }
}

// {platform.android.config}
android {
    namespace = "shared.data.encryption"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility(libs.versions.android.jvmTarget.get())
        targetCompatibility(libs.versions.android.jvmTarget.get())
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
// {platform.android.config}
