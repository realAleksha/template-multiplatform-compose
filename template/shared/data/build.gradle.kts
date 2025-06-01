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
                optIn("kotlin.ExperimentalStdlibApi")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("kotlin.io.encoding.ExperimentalEncodingApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.datetime.format.FormatStringsInDatetimeFormats")
            }
        }
        commonMain.dependencies {
            api(libs.bundles.ktor.common)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.json)
            implementation(libs.generativeai)
            implementation(libs.korlibs.crypto)
            implementation(libs.cashapp.paging.common)
            implementation(libs.multiplatform.settings.no.arg) // {dataflow.settings.multiplatform}
            implementation(libs.multiplatform.expressions.evaluator)
            implementation(libs.touchlab.stately.concurrent.collections) // {common.stately-collections}
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
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
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.java)
        }
        // {platform.jvm.dependencies}
        // {platform.mobile_and_desktop.dependencies}
        val mobileAndDesktopMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.paging.common)
                implementation(libs.androidx.datastore.preferences) // {dataflow.settings.datastore}
            }
        }
        androidMain.get().dependsOn(mobileAndDesktopMain) // {platform.android}
        iosMain.get().dependsOn(mobileAndDesktopMain) // {platform.ios}
        jvmMain.get().dependsOn(mobileAndDesktopMain) // {platform.jvm}
        // {platform.mobile_and_desktop.dependencies}
    }
}

// {platform.android.config}
android {
    namespace = "shared.data"
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
