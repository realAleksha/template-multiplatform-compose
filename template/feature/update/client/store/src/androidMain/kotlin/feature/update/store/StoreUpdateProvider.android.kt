package feature.update.store

import androidx.compose.runtime.Composable
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import shared.data.common.AppHolder
import shared.presentation.misc.extensions.findActivity

@Composable
internal actual fun onProvideFeatureContent() {
    // Not applicable
}

internal actual fun isSupported(): Boolean = true

internal actual fun onCheckForUpdates() {
    val context = AppHolder.app
    val activity = context.findActivity() ?: return
    val appUpdateManager = AppUpdateManagerFactory.create(context)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                activity,
                0
            )
        }
    }
}
