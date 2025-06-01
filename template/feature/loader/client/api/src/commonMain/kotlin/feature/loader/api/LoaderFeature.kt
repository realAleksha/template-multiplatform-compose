package feature.loader.api

import feature.common.api.Feature

interface LoaderFeature : Feature {

    suspend fun runLoading(title: String, block: suspend () -> Unit)
}