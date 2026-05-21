import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag // {platform.jvm}
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.application) // {platform.android}
    alias(libs.plugins.sqldelight) // {data.database.sqldelight}
    alias(libs.plugins.ksp) // {common.ksp}
    alias(libs.plugins.room) // {data.database.room}
}
kotlin {
    // {platform.android.target}
    androidTarget()
    // {platform.android.target}
    // {platform.ios.target}
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "App"
            isStatic = true
            linkerOpts.add("-lsqlite3") // {data.database.sqlite-linker}
        }
    }
    // {platform.ios.target}
    // {platform.js.target}
    js {
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
    // {platform.wasmJs.target}
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
            }
            useCommonJs()
        }
        binaries.executable()
    }
    // {platform.wasmJs.target}
    // {platform.jvm.target}
    jvm()
    // {platform.jvm.target}
    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.time.ExperimentalTime")
                optIn("kotlinx.coroutines.FlowPreview")
                optIn("kotlin.js.ExperimentalWasmJsInterop")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("androidx.compose.ui.ExperimentalComposeUiApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.serialization.InternalSerializationApi")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.kotlin.logging)
            implementation(libs.napier)
            implementation(libs.androidx.room.runtime) // {data.database.room}
            implementation(libs.androidx.sqlite) // {data.database.sqlite}
            implementation(libs.sqldelight.androidx.paging) // {data.database.sqldelight}
            implementation(libs.sqldelight.coroutines) // {data.database.sqldelight}
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
            implementation(projects.feature.update.client.api)
            implementation(projects.feature.update.client.sideload)
        }
        // {platform.android.dependencies}
        androidMain.dependencies {
            implementation(libs.androidx.splashscreen)
            implementation(libs.androidx.sqlite.framework) // {data.database.sqlite}
            implementation(libs.sqldelight.android.driver) // {data.database.sqldelight}
        }
        // {platform.android.dependencies}
        // {platform.ios.dependencies}
        iosMain.dependencies {
            implementation(libs.androidx.sqlite.framework) // {data.database.sqlite}
            implementation(libs.sqldelight.native.driver) // {data.database.sqldelight}
            implementation(libs.touchlab.stately.common) // {data.database.sqldelight}
            implementation(libs.touchlab.stately.isolate) // {data.database.sqldelight}
            implementation(libs.touchlab.stately.iso.collections) // {data.database.sqldelight}
        }
        // {platform.ios.dependencies}
        // {platform.js.dependencies}
        jsMain.dependencies {
            implementation(libs.androidx.sqlite.web)  // {data.database.sqlite}
            implementation(libs.sqldelight.web.worker.driver) // {data.database.sqldelight}
            implementation(npm("sql.js", "1.10.3")) // {data.database.sqldelight}
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqldelight.get())) // {data.database.sqldelight}
            implementation(devNpm("copy-webpack-plugin", "9.1.0")) // {data.database.sqldelight}
        }
        // {platform.js.dependencies}
        // {platform.wasmJs.dependencies}
        wasmJsMain.dependencies {
            implementation(libs.androidx.sqlite.web)  // {data.database.sqlite}
            implementation(libs.sqldelight.web.worker.driver) // {data.database.sqldelight}
            implementation(npm("sql.js", "1.10.3")) // {data.database.sqldelight}
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqldelight.get())) // {data.database.sqldelight}
            implementation(devNpm("copy-webpack-plugin", "9.1.0")) // {data.database.sqldelight}
        }
        // {platform.wasmJs.dependencies}
        // {platform.jvm.dependencies}
        jvmMain.dependencies {
            implementation(libs.slf4j.simple) // {kotlin.logging}
            implementation(compose.desktop.currentOs)
            implementation(libs.androidx.sqlite.bundled) // {data.database.sqlite}
            implementation(libs.sqldelight.sqlite.driver) // {data.database.sqldelight}
        }
        // {platform.jvm.dependencies}
    }
}
// {platform.android.config}
android {
    namespace = "kotli" // {kotli.namespace}
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "kotli" // {kotli.namespace}
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
            packageName = "kotli" // {kotli.name}
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

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
// {platform.jvm.config}
// {common.ksp.config}
dependencies {
    add("kspAndroid", libs.androidx.room.compiler) // {platform.android}
    add("kspJs", libs.androidx.room.compiler) // {platform.js}
    add("kspWasmJs", libs.androidx.room.compiler) // {platform.wasmJs}
    add("kspJvm", libs.androidx.room.compiler) // {platform.jvm}
    add("kspIosArm64", libs.androidx.room.compiler) // {platform.ios}
    add("kspIosSimulatorArm64", libs.androidx.room.compiler) // {platform.ios}
}
// {common.ksp.config}
// {data.database.room.config}
room3 {
    schemaDirectory("$projectDir/schemas")
}
// {data.database.room.config}
// {sqldelight.config}
sqldelight {
    databases {
        create("SqlDelightDb") {
            packageName.set("kotli.common.data.source.database.sqldelight") // {kotli.namespace}
            generateAsync.set(true)
        }
    }
}
// {sqldelight.config}