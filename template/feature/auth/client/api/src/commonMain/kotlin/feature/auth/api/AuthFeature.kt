package feature.auth.api

import feature.common.api.Feature
import kotlinx.coroutines.flow.Flow

interface AuthFeature : Feature {

    fun getAuthUser(): Flow<AuthUser?>

    fun startSignInFlow(title: String? = null)

    fun startSignOutFlow()

    fun startSignInWithEmailFlow()

    fun startSignInWithGoogleFlow()
}