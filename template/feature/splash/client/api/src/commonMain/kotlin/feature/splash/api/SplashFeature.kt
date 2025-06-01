package feature.splash.api

import feature.common.api.Feature

interface SplashFeature : Feature {

    fun show()

    fun hide()
}