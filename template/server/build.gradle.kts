plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.serialization.json)
    implementation(projects.shared.domain)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

// {platform.js.config}
tasks {
    val resourcesMain by lazy { project.layout.buildDirectory.dir("resources/main").get().asFile }
    register("runDevJs") {
        group = "spa"
        dependsOn(":client:jsBrowserDevelopmentExecutableDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/js/developmentExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("run")
    }
    register("runProdJs") {
        group = "spa"
        dependsOn(":client:jsBrowserDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/js/productionExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("run")
    }
    register("assembleJs") {
        group = "spa"
        dependsOn(":client:jsBrowserDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/js/productionExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("assemble")
    }
}
// {platform.js.config}
// {platform.wasmJs.config}
tasks {
    val resourcesMain by lazy { project.layout.buildDirectory.dir("resources/main").get().asFile }
    register("runDevWasmJs") {
        group = "spa"
        dependsOn(":client:wasmJsBrowserDevelopmentExecutableDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/wasmJs/developmentExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("run")
    }
    register("runProdWasmJs") {
        group = "spa"
        dependsOn(":client:wasmJsBrowserDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/wasmJs/productionExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("run")
    }
    register("assembleWasmJs") {
        group = "spa"
        dependsOn(":client:wasmJsBrowserDistribution")
        doLast {
            copy {
                from(File(rootDir, "client/build/dist/wasmJs/productionExecutable"))
                into(resourcesMain)
            }
        }
        finalizedBy("assemble")
    }
}
// {platform.wasmJs.config}
