package kotli.template.multiplatform.compose.feature.update.sideload

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.http.ktor.KtorHttpProcessor
import kotli.template.multiplatform.compose.data.settings.datastore.DataStoreProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.update.UpdateClientApiProcessor
import kotlin.reflect.KClass

object UpdateClientSideloadProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:update:client:sideload"

    override val featureName: String = "SideloadUpdate"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        UpdateClientApiProcessor::class,
        DataStoreProcessor::class,
        KtorHttpProcessor::class
    )
}
