package feature.review.market

import com.google.android.play.core.review.ReviewManagerFactory
import shared.data.common.AppHolder
import shared.presentation.misc.extensions.findActivity

internal actual fun isSupported(): Boolean = true

internal actual fun startReviewFlow() {
    val context = AppHolder.app
    val activity = context.findActivity() ?: return
    val manager = ReviewManagerFactory.create(context)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            manager.launchReviewFlow(activity, reviewInfo)
        }
    }
}
