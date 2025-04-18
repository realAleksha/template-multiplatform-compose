rootProject.name = "template"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://packages.jetbrains.team/maven/p/firework/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/firework/dev")
    }
}

// {platform.jvm.config}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
// {platform.jvm.config}

include(":app")
include(":backend")
include(":shared:data")
include(":shared:domain")
include(":shared:presentation")
