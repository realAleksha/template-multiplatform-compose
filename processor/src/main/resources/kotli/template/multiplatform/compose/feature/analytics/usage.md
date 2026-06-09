To use analytics, inject `AnalyticsFeature` into your components and call its methods.

Example:
```kotlin
val analytics: AnalyticsFeature = koin.get()
analytics.logEvent("screen_view", mapOf("screen_name" to "Home"))
```
