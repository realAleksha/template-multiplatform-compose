package feature.common.api

import androidx.compose.runtime.Stable
import kotlin.reflect.KClass

@Stable
interface FeatureContext {

    val features: List<Feature>

    fun <T : Feature> get(type: KClass<T>): T

    fun <T : Feature> getOrNull(type: KClass<T>): T?
}