package feature.analytics.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

internal actual fun isSupported(): Boolean = true

internal actual fun logFirebaseEvent(event: String, params: Map<String, String>) {
    Firebase.analytics.logEvent(event, params)
}

internal actual fun setFirebaseUserProperty(name: String, value: String) {
    Firebase.analytics.setUserProperty(name, value)
}

internal actual fun setFirebaseUserId(userId: String?) {
    Firebase.analytics.setUserId(userId)
}
