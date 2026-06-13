package shared.data.analytics

/**
 * Basic analytics source that delegates analytics events to multiple underlying sources.
 *
 * This class implements the [AnalyticsSource] interface.
 */
data class BasicAnalyticsSource(
    private val sources: List<AnalyticsSource> = emptyList()
) : AnalyticsSource {

    override fun setUserId(id: String?) {
        sources.forEach { it.setUserId(id) }
    }

    override fun setUserProperty(key: String, value: String?) {
        sources.forEach { it.setUserProperty(key, value) }
    }

    override fun onError(event: String, error: Throwable) {
        sources.forEach { it.onError(event, error) }
    }

    override fun onEvent(event: String, params: Map<String, String>) {
        sources.forEach { it.onEvent(event, params) }
    }

    override fun onScreenView(screenName: String, params: Map<String, String>) {
        sources.forEach { it.onScreenView(screenName, params) }
    }
}