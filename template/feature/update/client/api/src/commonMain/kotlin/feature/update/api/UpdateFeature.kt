package feature.update.api

import feature.common.api.Feature

interface UpdateFeature : Feature {

    fun checkForUpdates()
}