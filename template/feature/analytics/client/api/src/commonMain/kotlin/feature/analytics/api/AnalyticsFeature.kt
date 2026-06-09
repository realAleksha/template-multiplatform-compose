package feature.analytics.api

import feature.common.api.Feature

interface AnalyticsFeature : Feature {

    fun logEvent(event: String, params: Map<String, String> = emptyMap())

    fun setUserProperty(name: String, value: String)

    fun setUserId(userId: String?)

}
