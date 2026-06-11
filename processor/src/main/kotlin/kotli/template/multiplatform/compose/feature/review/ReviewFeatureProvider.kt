package kotli.template.multiplatform.compose.feature.review

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProvider
import kotli.template.multiplatform.compose.feature.review.market.ReviewClientMarketProcessor
import kotli.template.multiplatform.compose.feature.review.stub.ReviewClientStubProcessor

object ReviewFeatureProvider : UserFeatureProvider() {

    override fun getId(): String = "feature.review"

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ReviewClientApiProcessor,
        ReviewClientStubProcessor,
        ReviewClientMarketProcessor
    )
}
