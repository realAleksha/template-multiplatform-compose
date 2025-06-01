import org.jetbrains.compose.reload.ComposeHotRun // {platform.jvm}
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag // {platform.jvm}
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.application) // {platform.android}
    alias(libs.plugins.sqldelight) // {dataflow.database.sqldelight}
    alias(libs.plugins.ksp) // {common.ksp}
    alias(libs.plugins.room) // {dataflow.database.room}
    alias(libs.plugins.hot.reload) // {platform.jvm}
}
kotlin {
    // {platform.android.target}
    androidTarget()
    // {platform.android.target}
    // {platform.ios.target}
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "App"
            isStatic = true
            linkerOpts.add("-lsqlite3") // {dataflow.database.sqlite-linker}
        }
    }
    // {platform.ios.target}
    // {platform.js.target}
    js(IR) {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
            }
            useCommonJs()
        }
        binaries.executable()
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
                optIn("kotlinx.coroutines.FlowPreview")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("androidx.compose.ui.ExperimentalComposeUiApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.serialization.InternalSerializationApi")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(libs.koin.core)
            implementation(libs.kotlin.logging)
            implementation(libs.napier)
            implementation(libs.sqldelight.coroutines) // {dataflow.database.sqldelight}
            implementation(libs.touchlab.kermit)
            implementation(libs.supabase)
            implementation(libs.supabase.auth)
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.realtime)
            implementation(libs.supabase.functions)
            implementation(libs.supabase.compose.auth)
            implementation(libs.supabase.coil3.integration)
            implementation(projects.shared.data)
            implementation(projects.shared.domain)
            implementation(projects.shared.presentation)
            implementation(projects.feature.common.client.api)
            implementation(projects.feature.common.client.preview)
            implementation(projects.feature.loader.client.api)
            implementation(projects.feature.loader.client.basic)
            implementation(projects.feature.theme.client.api)
            implementation(projects.feature.theme.client.basic)
            implementation(projects.feature.splash.client.api)
            implementation(projects.feature.splash.client.basic)
            implementation(projects.feature.auth.client.api)
            implementation(projects.feature.auth.client.stub)
            implementation(projects.feature.auth.client.supabase)
            implementation(projects.feature.passcode.client.api)
            implementation(projects.feature.passcode.client.basic)
            implementation(projects.feature.payments.client.api)
            implementation(projects.feature.payments.client.revenuecat)
            implementation(projects.feature.navigation.client.api)
            implementation(projects.feature.navigation.client.basic)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.androidx.splashscreen)
            implementation(libs.sqldelight.android.driver) // {dataflow.database.sqldelight}
        }
        // {platform.android.dependencies}
        // {platform.ios.dependencies}
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver) // {dataflow.database.sqldelight}
            implementation(libs.touchlab.stately.common) // {dataflow.database.sqldelight}
            implementation(libs.touchlab.stately.isolate) // {dataflow.database.sqldelight}
            implementation(libs.touchlab.stately.iso.collections) // {dataflow.database.sqldelight}
        }
        // {platform.ios.dependencies}
        // {platform.js.dependencies}
        jsMain.dependencies {
            implementation(libs.sqldelight.web.worker.driver) // {dataflow.database.sqldelight}
            implementation(libs.touchlab.stately.iso.collections.js) // {dataflow.database.sqldelight}
            implementation(npm("sql.js", "1.10.3")) // {dataflow.database.sqldelight}
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqldelight.get())) // {dataflow.database.sqldelight}
            implementation(devNpm("copy-webpack-plugin", "9.1.0")) // {dataflow.database.sqldelight}
        }
        // {platform.js.dependencies}
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            implementation(libs.slf4j.simple) // {kotlin.logging}
            implementation(compose.desktop.currentOs)
            implementation(libs.sqldelight.sqlite.driver) // {dataflow.database.sqldelight}
        }
        // {platform.jvm.dependencies}
        // {platform.mobile_and_desktop.dependencies}
        val mobileAndDesktopMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.androidx.room.runtime) // {dataflow.database.room}
                implementation(libs.sqlite.bundled) // {dataflow.database.sqlite}
            }
        }
        // {platform.mobile_and_desktop.dependencies}
        androidMain.get().dependsOn(mobileAndDesktopMain) // {platform.android}
        iosMain.get().dependsOn(mobileAndDesktopMain) // {platform.ios}
        jvmMain.get().dependsOn(mobileAndDesktopMain) // {platform.jvm}
    }
}
// {sqldelight.config}
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("kotli.app.common.data.source.database.sqldelight")
            generateAsync.set(true)
        }
    }
}
// {sqldelight.config}
// {platform.android.config}
android {
    namespace = "kotli.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "kotli.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        named("debug") {
            storeFile = file("assemble/android/debug.keystore")
            keyAlias = "androiddebugkey"
            storePassword = "android"
            keyPassword = "android"
        }
    }
    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
        named("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "assemble/proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(libs.versions.android.jvmTarget.get())
        targetCompatibility(libs.versions.android.jvmTarget.get())
    }
}
// {platform.android.config}
// {platform.jvm.config}
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "kotli.app"
            packageVersion = "1.0.0"
            modules(
                "java.sql",
                "java.net.http",
                "jdk.unsupported",
                "jdk.security.auth",
            )
        }
        buildTypes.release.proguard {
            obfuscate.set(false)
            configurationFiles.from(project.file("assemble/proguard-rules.pro"))
        }
    }
}
tasks.register<ComposeHotRun>("runHot") {
    group = "compose desktop"
    mainClass.set("MainKt")
}
composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
// {platform.jvm.config}
// {common.ksp.config}
dependencies {
    add("kspAndroid", libs.androidx.room.compiler) // {platform.android}
    add("kspJvm", libs.androidx.room.compiler) // {platform.jvm}
    add("kspIosX64", libs.androidx.room.compiler) // {platform.ios}
    add("kspIosArm64", libs.androidx.room.compiler) // {platform.ios}
    add("kspIosSimulatorArm64", libs.androidx.room.compiler) // {platform.ios}
}
// {common.ksp.config}
// {dataflow.database.room.config}
room {
    schemaDirectory("$projectDir/schemas")
}
// {dataflow.database.room.config}
