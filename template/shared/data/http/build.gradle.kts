plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
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
            api(libs.bundles.ktor.common)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        // {platform.android.dependencies}
        // {platform.ios.dependencies}
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        // {platform.ios.dependencies}
        // {platform.js.dependencies}
         jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        // {platform.js.dependencies}
        // {platform.wasmJs.dependencies}
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        // {platform.wasmJs.dependencies}
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            implementation(libs.ktor.client.java)
        }
        // {platform.jvm.dependencies}
    }
}

// {platform.android.config}
android {
    namespace = "shared.data.http"
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
