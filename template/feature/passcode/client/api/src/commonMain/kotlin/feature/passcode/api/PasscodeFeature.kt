package feature.passcode.api

import feature.common.Feature

interface PasscodeFeature : Feature {

    fun setPasscode()

    fun resetPasscode()
}