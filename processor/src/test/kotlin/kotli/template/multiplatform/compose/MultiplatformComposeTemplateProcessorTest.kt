@file:OptIn(ExperimentalPathApi::class)

package kotli.template.multiplatform.compose

import kotli.engine.DefaultTemplateRegistry
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureProvider
import kotli.engine.generator.GradleProjectGenerator
import kotli.engine.generator.PathOutputGenerator
import kotli.engine.generator.ZipOutputGenerator
import kotli.engine.model.Feature
import kotli.engine.model.Layer
import kotli.template.multiplatform.compose.platform.client.android.AndroidPlatformProcessor
import kotli.template.multiplatform.compose.platform.client.jvm.JvmPlatformProcessor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.test.Test

class MultiplatformComposeTemplateProcessorTest {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val processor = MultiplatformComposeTemplateProcessor
    private val registry = DefaultTemplateRegistry(processor)

    private fun buildPath(): Path {
        return File("build/template").toPath().toAbsolutePath().also { it.deleteRecursively() }
    }

    private fun commands(features: Collection<Feature> = listOf(Feature(AndroidPlatformProcessor.ID))): Array<String> {
        val commands = mutableListOf<Array<String>>()
        features.find { it.id == AndroidPlatformProcessor.ID }?.let {
            commands.add(arrayOf("signingReport", "assembleDebug"))
        }
        features.find { it.id == JvmPlatformProcessor.ID }?.let {
            commands.add(arrayOf("packageDistributionForCurrentOS"))
        }
        return commands.first()
    }

    @Test
    fun `icon, title and description exist`() {
        Assertions.assertNotNull(processor.getIcon())
        Assertions.assertNotNull(processor.getTitle())
        Assertions.assertNotNull(processor.getDescription())
        processor.getFeatureProviders()
            .filter { provider -> provider.getProcessors().any { !it.isInternal() } }
            .forEach { provider ->
                Assertions.assertNotNull(provider.getTitle(), "NULL title :: ${provider::class.java.simpleName}")
            }
    }

    @Test
    fun `features have title and description`() {
        processor.getFeatureProviders()
            .filter { provider -> provider.getProcessors().any { !it.isInternal() } }
            .forEach { provider ->
                provider.getProcessors()
                    .filter { !it.isInternal() }
                    .forEach { processor ->
                        Assertions.assertNotNull(processor.getTitle(), processor.getId())
                        Assertions.assertNotNull(processor.getDescription(), processor.getId())
                    }
            }
    }

    @Test
    fun `feature providers are unique`() {
        val providers = processor.getFeatureProviders()
        val notUnique = providers.groupBy(FeatureProvider::getId).filter { it.value.size > 1 }
        notUnique.forEach { group ->
            Assertions.fail(group.value.toString())
        }
    }

    @Test
    fun `feature processors are unique`() {
        val processors = processor.getFeatureProviders().map { it.getProcessors() }.flatten()
        val notUnique = processors.groupBy(FeatureProcessor::getId).filter { it.value.size > 1 }
        notUnique.forEach { group ->
            Assertions.fail(group.value.toString())
        }
    }

    @Test
    fun `compose template in memory`() {
        runBlocking {
            val output = ByteArrayOutputStream()
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "myApp",
            )
            val generator = PathOutputGenerator(registry = registry)
            val zipGenerator = ZipOutputGenerator(output, generator)
            zipGenerator.generate(layer)
            Assertions.assertTrue(output.size() > 50000)
        }
    }

    @Test
    fun `compose template without features`() {
        runBlocking {
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "myApp",
                features = listOf(
                    Feature(AndroidPlatformProcessor.ID)
                )
            )
            val generator = PathOutputGenerator(buildPath(), registry)
            val gradleGenerator = GradleProjectGenerator(commands(layer.features), generator)
            gradleGenerator.generate(layer)
        }
    }

    @Test
    fun `compose template with all features`() {
        runBlocking {
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "myApp",
            )
            val generator = PathOutputGenerator(buildPath(), registry, fat = true)
            val gradleGenerator = GradleProjectGenerator(commands(), generator)
            gradleGenerator.generate(layer)
        }
    }

    @Test
    fun `compose template with random features`() {
        runBlocking {
            val processors = processor.getFeatureProviders()
                .map { it.getProcessors() }
                .flatten()
                .filter { !it.isInternal() }
            val features = mutableSetOf<Feature>()
            repeat(Random().nextInt(1, processors.size + 1)) {
                features.add(Feature(processors.random().getId()))
            }
            features.add(Feature(AndroidPlatformProcessor.ID))
            logger.debug("features :: {} -> {}", features.size, features.map { it.id })
            logger.debug(
                "processors :: features:\n{}\n",
                processors.joinToString(",\n") { "Feature(${it.javaClass.name}.getId())" })
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                features = features.toList(),
                namespace = "my.app",
                name = "myApp",
            )
            val generator = PathOutputGenerator(buildPath(), registry)
            val gradleGenerator = GradleProjectGenerator(commands(features), generator)
            gradleGenerator.generate(layer)
        }
    }
}