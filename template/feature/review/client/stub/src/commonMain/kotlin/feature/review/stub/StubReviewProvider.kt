package feature.review.stub

import androidx.compose.runtime.Composable
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.koin.KoinActionFeatureProvider
import feature.review.api.ReviewFeature
import kotlin.reflect.KClass

class StubReviewProvider : KoinActionFeatureProvider(), FeaturePreview, ReviewFeature {

    override val name: String = "Stub Review"

    override val type: KClass<out Feature> = ReviewFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("review()") {
            review()
        }
    )

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        content()
    }

    override fun review() {}

}
