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
include(":feature:common:client:api")
include(":feature:common:client:koin")
include(":feature:common:client:preview")
include(":feature:loader:client:api")
include(":feature:loader:client:basic")
include(":feature:ads:client:api")
include(":feature:ads:client:stub")
include(":feature:analytics:client:api")
include(":feature:analytics:client:stub")
include(":feature:auth:client:api")
include(":feature:auth:client:base")
include(":feature:auth:client:stub")
include(":feature:auth:client:supabase")
include(":feature:navigation:client:api")
include(":feature:navigation:client:basic")
include(":feature:onboarding:client:api")
include(":feature:onboarding:client:basic")
include(":feature:passcode:client:api")
include(":feature:passcode:client:basic")
include(":feature:payments:client:api")
include(":feature:payments:client:revenuecat")
include(":feature:profile:client:api")
include(":feature:profile:client:stub")
include(":feature:rate:client:api")
include(":feature:rate:client:stub")
include(":feature:settings:client:api")
include(":feature:settings:client:stub")
include(":feature:splash:client:api")
include(":feature:splash:client:basic")
include(":feature:support:client:api")
include(":feature:support:client:stub")
include(":feature:theme:client:api")
include(":feature:theme:client:basic")
include(":feature:update:client:api")
include(":feature:update:client:stub")