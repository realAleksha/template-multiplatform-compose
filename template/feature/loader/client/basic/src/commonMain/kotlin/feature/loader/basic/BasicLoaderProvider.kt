package feature.loader.basic

import androidx.compose.runtime.Composable
import feature.common.api.Feature
import feature.common.api.FeatureNavContext
import feature.common.koin.KoinFeatureProvider
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.loader.api.LoaderFeature
import kotlinx.coroutines.delay
import shared.presentation.state.MutableViewState
import shared.presentation.state.UiState
import shared.presentation.state.ViewStateHandler
import shared.presentation.state.tryCatch
import kotlin.reflect.KClass

class BasicLoaderProvider : KoinFeatureProvider(), FeaturePreview, LoaderFeature {

    override val name: String = "Basic Loader"

    override val type: KClass<out Feature> = LoaderFeature::class

    private val state = BasicLoaderMutableState()

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction(
            name = "runLoading(title, block) - success",
            action = {
                runLoading("Some action") {
                    delay(1000)
                }
            }
        ),
        MethodCallsAction(
            name = "runLoading(title, block) - error",
            action = {
                runLoading("Some action") {
                    delay(1000)
                    error("Some error")
                }
            }
        )
    )

    override suspend fun runLoading(title: String, block: suspend () -> Unit) {
        state.tryCatch(
            title = title,
            onTry = {
                uiState = UiState.Blocking
                block()
                uiState = UiState.Ready
            }
        )
    }

    @Composable
    override fun onProvideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        ViewStateHandler(
            state = state,
            content = content
        )
    }

    private class BasicLoaderMutableState : MutableViewState(), BasicLoaderState
}