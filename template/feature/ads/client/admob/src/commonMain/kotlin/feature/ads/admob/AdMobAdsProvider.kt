package feature.ads.admob

import androidx.compose.runtime.Composable
import feature.ads.api.AdsFeature
import feature.common.api.Feature
import feature.common.api.FeatureContext
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.api.preview.MethodComposable
import feature.common.koin.KoinActionFeatureProvider
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

class AdMobAdsProvider(
    private val bannerAdUnitId: String? = null,
    private val interstitialAdUnitId: String? = null,
    private val rewardedAdUnitId: String? = null,
    private val nativeAdUnitId: String? = null
) : KoinActionFeatureProvider(), AdsFeature, FeaturePreview {

    override val name: String = "AdMob Ads"

    override val type: KClass<out Feature> = AdsFeature::class

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodCallsAction("showInterstitialAd()") {
            showInterstitialAd().collect {}
        },
        MethodCallsAction("showRewardedAd()") {
            showRewardedAd().collect {}
        },
        MethodComposable("BannerAd()") {
            BannerAd()
        },
        MethodComposable("NativeAd()") {
            NativeAd()
        }
    )

    @Composable
    override fun onProvideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        withDI {
            AdMobInit()
            content()
        }
    }

    override fun showInterstitialAd(): Flow<Boolean> = AdMobShowInterstitial(interstitialAdUnitId)

    override fun showRewardedAd(): Flow<Boolean> = AdMobShowRewarded(rewardedAdUnitId)

    @Composable
    override fun BannerAd() {
        AdMobBannerAd(bannerAdUnitId)
    }

    @Composable
    override fun NativeAd() {
        AdMobNativeAd(nativeAdUnitId)
    }
}

@Composable
internal expect fun AdMobInit()

internal expect fun AdMobShowInterstitial(adUnitId: String?): Flow<Boolean>

internal expect fun AdMobShowRewarded(adUnitId: String?): Flow<Boolean>

@Composable
internal expect fun AdMobBannerAd(adUnitId: String?)

@Composable
internal expect fun AdMobNativeAd(adUnitId: String?)
