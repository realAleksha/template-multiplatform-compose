package feature.passcode.api

import feature.common.api.Feature

interface PasscodeFeature : Feature {

    suspend fun isPasscodeSet(): Boolean

    fun startSetPasscodeFlow()

    fun startResetPasscodeFlow()

    fun startForgotPasscodeFlow()
}