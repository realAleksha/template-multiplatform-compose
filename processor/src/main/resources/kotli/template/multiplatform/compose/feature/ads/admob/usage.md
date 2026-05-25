# Usage

## API Interface (AdsFeature)
- `fun showInterstitialAd(): Flow<Boolean>` - Shows an interstitial ad and returns a flow that completes when the ad is closed.
- `fun showRewardedAd(): Flow<Boolean>` - Shows a rewarded ad and returns a flow that completes when the ad is closed.
- `@Composable fun BannerAd()` - A Composable that displays a banner ad.
- `@Composable fun NativeAd()` - A Composable that displays a native ad.

## Installation

To add the AdMob Feature to your project, include the following dependencies in your module's `build.gradle.kts` file:

```kotlin
// In your commonMain dependencies
implementation(projects.feature.ads.client.api)
implementation(projects.feature.ads.client.admob)
```

Then, register the feature provider in your application's DI container (e.g., in AppConfig.kt):

```kotlin
// Using Koin
single<AdsFeature> {
    AdMobAdsProvider(
        bannerAdUnitId = "your-banner-id",
        interstitialAdUnitId = "your-interstitial-id",
        rewardedAdUnitId = "your-rewarded-id",
        nativeAdUnitId = "your-native-id"
    )
}
```

## Configuration

To configure the `AdMobAdsProvider`:

Constructor Parameter | Default Value | Description
---------------------|---------------|-------------
bannerAdUnitId | null (uses default) | AdMob Ad Unit ID for Banner ads
interstitialAdUnitId | null (uses default) | AdMob Ad Unit ID for Interstitial ads
rewardedAdUnitId | null (uses default) | AdMob Ad Unit ID for Rewarded ads
nativeAdUnitId | null (uses default) | AdMob Ad Unit ID for Native ads

### Platform-specific setup

#### Android
1. Add your AdMob App ID to `feature/ads/client/admob/src/androidMain/AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
```
2. For more details, see [AdMob Android Get Started](https://developers.google.com/admob/android/quick-start).

#### iOS
1. Update your main app's `Info.plist` with `GADApplicationIdentifier`:
```xml
<key>GADApplicationIdentifier</key>
<string>ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy</string>
<key>SKAdNetworkItems</key>
<array>
    <dict>
        <key>SKAdNetworkIdentifier</key>
        <string>cstr6suwn9.skadnetwork</string>
    </dict>
</array>
```
2. For more details, see [AdMob iOS Get Started](https://developers.google.com/admob/ios/quick-start).

## Usage

To use the ads feature in your application:

```kotlin
val adsFeature: AdsFeature = get()

// Showing an interstitial ad
scope.launch {
    adsFeature.showInterstitialAd().collect { finished ->
        if (finished) {
            // Ad was dismissed
        }
    }
}

// Displaying a banner
@Composable
fun MyScreen() {
    Column {
        Text("My Content")
        adsFeature.BannerAd()
    }
}
```
