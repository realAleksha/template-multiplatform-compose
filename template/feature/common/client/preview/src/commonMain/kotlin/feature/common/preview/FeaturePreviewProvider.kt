package feature.common.preview

import feature.common.api.Feature
import kotlin.reflect.KClass

interface FeaturePreviewProvider {

    val name: String

    val type: KClass<out Feature>

    fun getMethods(): List<FeatureMethod>
}