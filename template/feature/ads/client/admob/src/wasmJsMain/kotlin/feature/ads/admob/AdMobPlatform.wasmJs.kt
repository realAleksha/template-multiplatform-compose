package feature.ads.admob

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
internal actual fun AdMobInit() {
}

internal actual fun AdMobShowInterstitial(adUnitId: String?): Flow<Boolean> = flowOf(false)

internal actual fun AdMobShowRewarded(adUnitId: String?): Flow<Boolean> = flowOf(false)

@Composable
internal actual fun AdMobBannerAd(adUnitId: String?) {
}

@Composable
internal actual fun AdMobNativeAd(adUnitId: String?) {
}
