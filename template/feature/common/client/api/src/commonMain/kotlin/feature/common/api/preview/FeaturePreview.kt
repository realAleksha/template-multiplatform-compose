package feature.common.api.preview

import feature.common.api.Feature
import kotlin.reflect.KClass

interface FeaturePreview {

    val name: String

    val type: KClass<out Feature>

    fun getMethods(): List<FeatureMethod>
}