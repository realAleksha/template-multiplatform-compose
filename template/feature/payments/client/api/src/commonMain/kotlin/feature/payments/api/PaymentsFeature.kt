package feature.payments.api

import feature.common.api.Feature

interface PaymentsFeature : Feature {

    fun showPaywall(offeringId: String? = null)

    suspend fun hasAccess(entitlementId: String): Boolean
}