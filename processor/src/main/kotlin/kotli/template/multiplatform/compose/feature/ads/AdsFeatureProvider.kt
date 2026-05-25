package kotli.template.multiplatform.compose.feature.ads

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProvider
import kotli.template.multiplatform.compose.feature.ads.admob.AdMobClientProcessor

object AdsFeatureProvider : UserFeatureProvider() {

    override fun getId(): String = "feature.ads"

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        AdsClientApiProcessor,
        AdsClientStubProcessor,
        AdMobClientProcessor
    )
}