## API Interface (PaymentsFeature)
- `fun showPaywall(offeringId: String? = null)` - Shows the paywall for in-app purchases and subscriptions. Optionally, a specific offering ID can be provided.
- `suspend fun hasEntitlement(entitlementId: String): Boolean` - Checks if the user has an active entitlement with the given ID.

## Installation

To add the Payments Feature to your project, include the following dependencies in your module's `build.gradle.kts` file:

```kotlin
// In your commonMain dependencies
implementation(projects.feature.payments.client.api)
implementation(projects.feature.payments.client.revenuecat)
```

Then, register the feature provider in your application's DI container (e.g., in AppConfig.kt):

```kotlin
// Using Koin
single<PaymentsFeature> { 
    RevenueCatPaymentsProvider(
        apiKey = "...",
        apiUserId = "..."
    ) 
}
```

## Configuration
The `RevenueCatPaymentsProvider` accepts the following parameters:
- `apiKey` (required): The RevenueCat API key.
- `apiUserId` (required): The unique identifier for the user.

## Usage
To get an instance of the `PaymentsFeature` provider, use Koin's dependency injection:

```kotlin
// Get the PaymentsFeature instance from Koin
val paymentsFeature: PaymentsFeature = get()

// Or inject it into your class
class YourClass(private val paymentsFeature: PaymentsFeature)
```

Example usage:

```kotlin
// Check if user has "premium" entitlement
val isPremium = paymentsFeature.hasEntitlement("premium")

// Show the paywall
paymentsFeature.showPaywall()
```