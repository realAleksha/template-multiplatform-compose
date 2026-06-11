package kotli.template.multiplatform.compose.feature.review.market

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.review.ReviewClientApiProcessor
import kotlin.reflect.KClass

object ReviewClientMarketProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:review:client:market"

    override val featureName: String = "MarketReviewProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        ReviewClientApiProcessor::class
    )
}
