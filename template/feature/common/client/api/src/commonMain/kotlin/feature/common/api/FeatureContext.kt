package feature.common.api

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow

@Stable
interface FeatureContext {

    val debug: Boolean

    val features: List<Feature>

    fun getCurrentBackStackChanges(): Flow<List<Int>>

    fun getCurrentDestinationChanges(): Flow<Int>

    fun getDestinationId(route: Any): Int

    fun getCurrentDestination(): Int?

    fun replaceDestination(route: Any)

    fun restoreDestination(route: Any)

    fun pushDestination(route: Any)

    fun setDestination(route: Any)

    fun popDestination()
}