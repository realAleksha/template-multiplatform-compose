package kotli.template.multiplatform.compose.feature.review.stub

import kotli.engine.FeatureProcessor
import kotli.template.multiplatform.compose.feature.UserFeatureProcessor
import kotli.template.multiplatform.compose.feature.review.ReviewClientApiProcessor
import kotlin.reflect.KClass

object ReviewClientStubProcessor : UserFeatureProcessor() {

    override val moduleName: String = "feature:review:client:stub"

    override val featureName: String = "StubReviewProvider"

    override fun dependencies(): List<KClass<out FeatureProcessor>> = listOf(
        ReviewClientApiProcessor::class
    )
}
