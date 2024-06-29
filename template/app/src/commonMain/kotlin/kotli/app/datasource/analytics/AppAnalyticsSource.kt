package kotli.app.datasource.analytics

import shared.data.source.analytics.CompositeAnalyticsSource

/**
 * Decorator class for working with analytics events.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppAnalyticsSource : CompositeAnalyticsSource(
    listOf(
        // sources
    )
)