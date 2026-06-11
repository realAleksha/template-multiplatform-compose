package kotli.template.multiplatform.compose.feature.review

import kotli.template.multiplatform.compose.feature.UserFeatureProcessor

object ReviewClientApiProcessor : UserFeatureProcessor() {
    override val moduleName: String = "feature:review:client:api"
    override val featureName: String = "ReviewFeature"
    override fun isInternal(): Boolean = true
}
