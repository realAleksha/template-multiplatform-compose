package feature.common.preview

interface FeaturePreviewProvider {

    val name: String

    fun getMethods(): List<FeatureMethod>
}