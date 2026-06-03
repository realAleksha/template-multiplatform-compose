package feature.update.store

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
internal actual fun onProvideFeatureContent() {
    // Not applicable
}

internal actual fun onCheckForUpdates() {
    val url = NSURL(string = "itms-apps://itunes.apple.com/app/idYOUR_APP_ID")
    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}
