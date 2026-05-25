package feature.ads.api

import androidx.compose.runtime.Composable
import feature.common.api.Feature
import kotlinx.coroutines.flow.Flow

interface AdsFeature : Feature {

    fun showInterstitialAd(): Flow<Boolean>

    fun showRewardedAd(): Flow<Boolean>

    @Composable
    fun BannerAd()

    @Composable
    fun NativeAd()
}