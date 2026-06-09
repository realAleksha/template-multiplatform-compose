package feature.analytics.firebase

internal expect fun isSupported(): Boolean

internal expect fun logFirebaseEvent(event: String, params: Map<String, String>)

internal expect fun setFirebaseUserProperty(name: String, value: String)

internal expect fun setFirebaseUserId(userId: String?)
