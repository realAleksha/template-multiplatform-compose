package feature.common

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface FeatureContext {

    fun <F : Feature> get(): F

    fun getCurrentBackStackIdChanges(): Flow<Int>

    fun getCurrentBackStackId(): Int?

    fun getBackStackId(route: Any): Int

    fun replaceBackStack(route: Any)

    fun addBackStack(route: Any)

    fun setBackStack(route: Any)

    fun popBackStack()
}