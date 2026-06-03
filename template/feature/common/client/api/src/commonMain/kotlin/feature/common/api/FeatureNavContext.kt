package feature.common.api

import kotlinx.coroutines.flow.Flow

interface FeatureNavContext : FeatureContext {

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