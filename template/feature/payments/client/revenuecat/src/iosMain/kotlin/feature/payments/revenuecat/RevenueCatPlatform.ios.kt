package feature.payments.revenuecat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
internal actual fun OnProvideContent(apiKey: String, apiUserId: String) {
    LaunchedEffect(apiKey, apiUserId) {
        Purchases.configure(PurchasesConfiguration(apiKey) {
            appUserId = apiUserId
        })
    }
}

@Composable
internal actual fun OnPaywallRoute(
    offeringId: String?,
    onDismissRequest: () -> Unit
) {
    var offering by remember { mutableStateOf<Offering?>(null) }

    LaunchedEffect(offeringId) {
        if (offeringId != null) {
            Purchases.sharedInstance.getOfferings(
                onSuccess = { offerings ->
                    offering = offerings.all[offeringId]
                },
                onError = {
                    offering = null
                }
            )
        } else {
            offering = null
        }
    }

    Paywall(
        options = remember(offering) {
            PaywallOptions(dismissRequest = onDismissRequest) {
                this.offering = offering
            }
        }
    )
}

internal actual suspend fun hasEntitlement(entitlementId: String): Boolean =
    suspendCancellableCoroutine { continuation ->
        Purchases.sharedInstance.getCustomerInfo(
            onSuccess = { customerInfo ->
                continuation.resume(customerInfo.entitlements.active.containsKey(entitlementId))
            },
            onError = {
                continuation.resume(false)
            }
        )
    }
