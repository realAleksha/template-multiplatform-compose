package feature.splash.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import feature.common.api.Feature
import feature.common.api.FeatureContext
import feature.common.koin.KoinFeatureProvider
import feature.common.preview.FeatureMethod
import feature.common.preview.FeaturePreviewProvider
import feature.common.preview.method.MethodCallsAction
import feature.splash.api.SplashFeature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import shared.presentation.ui.component.DsCircularProgressIndicator
import kotlin.reflect.KClass

class BasicSplashProvider(
    show: Boolean = true
) : KoinFeatureProvider(), FeaturePreviewProvider, SplashFeature {

    private val previewScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    private val visibleState by lazy { mutableStateOf(show) }

    override val name: String = "Basic Splash"

    override val type: KClass<out Feature> = SplashFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("setVisible(true)", {
            previewScope.launch {
                setVisible(true)
                delay(3000)
                setVisible(false)
            }
        })
    )

    @Composable
    override fun onProvideContent(
        context: FeatureContext,
        content: @Composable (() -> Unit)
    ) {
        content()
        SplashBlock()
    }

    @Composable
    private fun SplashBlock() {
        AnimatedVisibility(
            visible = visibleState.value,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                DsCircularProgressIndicator(
                    color = Color.DarkGray,
                )
            }
        }
    }

    override fun setVisible(visible: Boolean) {
        visibleState.value = visible
    }
}