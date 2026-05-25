package kotli.template.multiplatform.compose.feature.ads.admob

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.ads.AdsClientApiProcessor
import kotlin.reflect.KClass

object AdMobClientProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:ads:client:admob"

    override val featureName: String = "AdMob"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        AdsClientApiProcessor::class
    )
}
