package feature.review.market

import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene
import platform.UIKit.UISceneActivationStateForegroundActive

internal actual fun isSupported(): Boolean = true

internal actual fun startReviewFlow() {
    val scene = UIApplication.sharedApplication.connectedScenes.allObjects
        .filterIsInstance<UIWindowScene>()
        .firstOrNull { it.activationState == UISceneActivationStateForegroundActive }
    
    if (scene != null) {
        SKStoreReviewController.requestReviewInScene(scene)
    } else {
        @Suppress("DEPRECATION")
        SKStoreReviewController.requestReview()
    }
}
