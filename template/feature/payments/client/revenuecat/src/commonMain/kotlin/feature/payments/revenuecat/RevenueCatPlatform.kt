package feature.payments.revenuecat

import androidx.compose.runtime.Composable

internal expect suspend fun hasEntitlement(entitlementId: String): Boolean

@Composable
internal expect fun OnProvideContent(apiKey: String, apiUserId: String)

@Composable
internal expect fun OnPaywallRoute(offeringId: String?, onDismissRequest: () -> Unit)

