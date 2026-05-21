## API Interface (UpdateFeature)
- `fun checkForUpdates()` - Initiates a check for available updates. Depending on the resolved update state, it may show an optional update dialog, a force update screen, or proceed to install a previously downloaded update.

## Installation

To add the Sideload Update feature to your project, include the following modules in your project configuration:

```kotlin
implementation(projects.feature.update.client.api)
implementation(projects.feature.update.client.sideload)
```

Then, register the feature provider in your application's DI container (e.g., in `AppConfig.kt`):

```kotlin
single<UpdateFeature> {
    SideloadUpdateProvider(
        httpSource = get(),
        settingsSource = get(),
        resolver = DefaultSideloadUpdateStateResolver(
            metadataUrl = "https://example.com/update.json",
            httpSource = get(),
            currentVersionCode = 1
        )
    )
}
```

## Configuration
To configure the `SideloadUpdateProvider`:

Constructor Parameter | Description
---------------------|-------------
httpSource | The HttpSource instance used for downloading the update file.
settingsSource | The SettingsSource instance used for persistent storage of the update state (e.g., keeping track of a downloaded but not yet installed update).
resolver | The SideloadUpdateStateResolver used to fetch and determine the current update state from a remote or local source.

## Usage
To use the update feature in your application, inject the `UpdateFeature` using Koin:

```kotlin
// Get the UpdateFeature instance
val updateFeature: UpdateFeature = get()

// Check for updates manually
updateFeature.checkForUpdates()
```

The feature also automatically checks for updates when provided as a content in `onProvideContent` (usually called during app startup).
