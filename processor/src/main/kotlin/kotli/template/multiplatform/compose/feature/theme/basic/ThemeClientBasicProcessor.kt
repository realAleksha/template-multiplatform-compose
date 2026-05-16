package kotli.template.multiplatform.compose.feature.theme.basic

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.data.settings.datastore.DataStoreProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.theme.ThemeClientApiProcessor
import kotlin.reflect.KClass

object ThemeClientBasicProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:theme:client:basic"

    override val featureName: String = "BasicThemeProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        ThemeClientApiProcessor::class,
        DataStoreProcessor::class,
    )
}