package feature.update.store

import androidx.compose.runtime.Composable
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.koin.KoinActionFeatureProvider
import feature.update.api.UpdateFeature
import kotlin.reflect.KClass

class StoreUpdateProvider : KoinActionFeatureProvider(), FeaturePreview, UpdateFeature {

    override val name: String = "Store Update"

    override val type: KClass<out Feature> = UpdateFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction(
            "checkForUpdates()",
            action = ::checkForUpdates
        )
    )

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable () -> Unit) {
        onProvideFeatureContent()
        content()
    }

    override fun checkForUpdates() {
        onCheckForUpdates()
    }

}

@Composable
internal expect fun onProvideFeatureContent()

internal expect fun onCheckForUpdates()
