## API Interface (UpdateFeature)
- `fun checkForUpdates()` - Manual check for updates. Redirects to the store page of the app (Google Play on Android, App Store on iOS).

## Installation

To add the Store Update feature to your project, include the following modules in your project configuration:

```kotlin
implementation(projects.feature.update.client.api)
implementation(projects.feature.update.client.store)
```

Then, register the feature provider in your application's DI container (e.g., in `AppConfig.kt`):

```kotlin
single<UpdateFeature> {
    StoreUpdateProvider()
}
```

## Usage
To use the update feature in your application, inject the `UpdateFeature` using Koin:

```kotlin
// Get the UpdateFeature instance
val updateFeature: UpdateFeature = get()

// Check for updates manually
updateFeature.checkForUpdates()
```

The feature also automatically checks for updates when provided as a content in `onProvideContent` (usually called during app startup in `AppActivity`).

On Android, this feature uses the native **Google Play In-App Updates** SDK for automatic checks. It handles the update flow within the app if an update is available and allowed.
