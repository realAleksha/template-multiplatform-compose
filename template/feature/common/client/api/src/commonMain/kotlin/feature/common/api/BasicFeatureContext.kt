package feature.common.api

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
data class BasicFeatureContext(
    override val features: List<Feature>
) : FeatureContext {

    override fun <T : Feature> get(type: KClass<T>): T {
        return features.first { type.isInstance(it) } as T
    }

    override fun <T : Feature> getOrNull(type: KClass<T>): T? {
        return features.firstOrNull { type.isInstance(it) } as? T
    }
}