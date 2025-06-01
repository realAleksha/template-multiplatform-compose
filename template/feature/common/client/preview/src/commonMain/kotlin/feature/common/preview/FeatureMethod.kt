package feature.common.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface FeatureMethod {

    val name: String

    @Composable
    fun preview()
}