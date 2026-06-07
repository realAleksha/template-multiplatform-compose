package feature.ads.admob

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.BasicAds
import app.lexilabs.basic.ads.InterstitialAdHandler
import app.lexilabs.basic.ads.RewardedAdHandler
import app.lexilabs.basic.ads.composable.BannerAd
import app.lexilabs.basic.ads.composable.NativeAd
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal actual fun isSupported(): Boolean = true

@Composable
internal actual fun AdMobInit() {
    BasicAds.Initialize()
}

internal actual fun AdMobShowInterstitial(adUnitId: String?): Flow<Boolean> = callbackFlow {
    val ad = InterstitialAdHandler(null)
    ad.load(
        adUnitId = adUnitId ?: app.lexilabs.basic.ads.AdUnitId.INTERSTITIAL_DEFAULT,
        onLoad = {
            ad.setListeners(
                onFailure = { close(it) },
                onDismissed = {
                    trySend(true)
                    close()
                }
            )
            ad.show()
        },
        onFailure = { close(it) }
    )
    awaitClose()
}

internal actual fun AdMobShowRewarded(adUnitId: String?): Flow<Boolean> = callbackFlow {
    val ad = RewardedAdHandler(null)
    ad.load(
        adUnitId = adUnitId ?: app.lexilabs.basic.ads.AdUnitId.REWARDED_DEFAULT,
        onLoad = {
            ad.setListeners(
                onFailure = { close(it) },
                onDismissed = {
                    close()
                }
            )
            ad.show(onRewardEarned = {
                trySend(true)
            })
        },
        onFailure = { close(it) }
    )
    awaitClose()
}

@Composable
internal actual fun AdMobBannerAd(adUnitId: String?) {
    BannerAd(adUnitId ?: app.lexilabs.basic.ads.AdUnitId.BANNER_DEFAULT)
}

@Composable
internal actual fun AdMobNativeAd(adUnitId: String?) {
    NativeAd(adUnitId ?: app.lexilabs.basic.ads.AdUnitId.NATIVE_DEFAULT)
}
