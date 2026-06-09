package feature.analytics.firebase

internal actual fun isSupported(): Boolean = false

internal actual fun logFirebaseEvent(event: String, params: Map<String, String>) {}

internal actual fun setFirebaseUserProperty(name: String, value: String) {}

internal actual fun setFirebaseUserId(userId: String?) {}
