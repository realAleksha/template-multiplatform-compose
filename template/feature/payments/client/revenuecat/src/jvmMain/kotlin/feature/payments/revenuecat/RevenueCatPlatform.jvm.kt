package feature.payments.revenuecat

import androidx.compose.runtime.Composable

@Composable
internal actual fun OnProvideContent(apiKey: String, apiUserId: String) {
}

@Composable
internal actual fun OnPaywallRoute(offeringId: String?, onDismissRequest: () -> Unit) {
}

internal actual suspend fun hasEntitlement(entitlementId: String): Boolean = false

internal actual fun isSupported(): Boolean = false
