package feature.common.koin

import androidx.compose.runtime.Composable
import feature.common.api.BaseFeatureProvider
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

abstract class KoinFeatureProvider : BaseFeatureProvider() {

    protected val koinApp by lazy { koinApplication(createEagerInstances = false) { modules(module { onProvideDI() }) } }

    @Composable
    protected fun withDI(context: KoinApplication? = null, content: @Composable () -> Unit) {
        KoinIsolatedContext(context ?: koinApp, content)
    }

    protected open fun Module.onProvideDI() = Unit
}