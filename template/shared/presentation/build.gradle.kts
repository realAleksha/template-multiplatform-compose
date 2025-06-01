plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library) // {platform.android}
}

kotlin {
    // {platform.android.target}
    androidTarget()
    // {platform.android.target}
    // {platform.ios.target}
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    // {platform.ios.target}
    // {platform.js.target}
    js(IR) {
        browser()
    }
    // {platform.js.target}
    // {platform.jvm.target}
    jvm()
    // {platform.jvm.target}
    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.time.ExperimentalTime")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            api(compose.foundation)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.json)
            api(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.filekit.compose)
            implementation(libs.markdown.renderer) // {userflow.component.markdown}
            implementation(libs.markdown.renderer.m3) // {userflow.component.markdown}
            implementation(libs.markdown.renderer.coil3) // {userflow.component.markdown}
            implementation(libs.cashapp.paging.compose.common)
            implementation(projects.shared.data)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            api(libs.androidx.appcompat)
            api(libs.androidx.activity.compose)
            api(libs.kotlinx.coroutines.android)
        }
        // {platform.android.dependencies}
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
        }
        // {platform.jvm.dependencies}
        // {platform.mobile_and_desktop.dependencies}
        val mobileAndDesktopMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.paging.compose)
            }
        }
        androidMain.get().dependsOn(mobileAndDesktopMain) // {platform.android}
//        iosMain.get().dependsOn(mobileAndDesktopMain) // {platform.ios}
//        jvmMain.get().dependsOn(mobileAndDesktopMain) // {platform.jvm}
        // {platform.mobile_and_desktop.dependencies}
        val skikoMain by creating {
            dependsOn(commonMain.get())
        }
        jsMain.get().dependsOn(skikoMain) // {platform.js}
        jvmMain.get().dependsOn(skikoMain) // {platform.jvm}
        nativeMain.get().dependsOn(skikoMain) // {platform.ios}
    }
}

// {platform.android.config}
android {
    namespace = "shared.presentation"
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
