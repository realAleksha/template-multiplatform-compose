package feature.payments.api

import feature.common.api.Feature

interface PaymentsFeature : Feature {

    fun showPaywall()
}