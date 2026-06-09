# Root Project Context

This repository is a composite build consisting of two main parts:
1. **Processor (`/processor`)**: The engine that configures and prepares the template.
2. **Template (`/template`)**: The source code of the multiplatform Compose project.

## Repository Structure

- `processor/`: Kotlin module containing the logic for template transformation. It scans the `template` and applies changes based on selected features.
- `template/`: A standalone Gradle project that serves as the blueprint for generated apps.
- `docs/`: Feature-specific documentation used by both the processor and the generated project.
- `images/`: Visual assets for documentation.

## How it works

The `processor` module treats the `template` directory as raw data. When a project is generated:
1. The processor copies the `template` files.
2. It applies `Processor` classes to include or remove code blocks based on markers.
3. It renames packages and performs other text replacements.

## Key Guidelines

- **Changes in `template`** must be accompanied by appropriate markers so the `processor` can handle them (e.g., if you add a new dependency, wrap it in platform/feature markers).
- **Changes in `processor`** usually involve adding new `FeatureProvider` or `FeatureProcessor` implementations to support new capabilities in the template.
- **Never modify the `template`** without considering how it affects the generation process in the `processor`.

## Adding a New Feature

When adding a new feature (e.g., `feature:update:client:store`):

1.  **Template Module**:
    - Create the module directory in `template/feature/...`.
    - Add `build.gradle.kts`. Use `alias(libs.plugins...)` for plugins and `projects.shared...` for internal dependencies.
    - Use `expect`/`actual` for platform-specific logic.
    - Register the module in `template/settings.gradle.kts`.
2.  **Processor**:
    - Create a `FeatureProcessor` (usually extending `UserFeatureProcessor`) in the `processor` module.
    - Set `moduleName` to the path of the template module (e.g., `feature:update:client:store`).
    - Register the processor in the corresponding `FeatureProvider`.
3.  **Documentation**:
    - Create documentation files in `processor/src/main/resources/.../<feature_name>/`:
        - `title.md`: Short name of the feature.
        - `description.md`: Brief overview of what the feature does.
        - `usage.md`: Detailed instructions on how to install and use the feature in the generated app.

## Feature Implementation Patterns

To maintain consistency across the project, follow these architectural patterns when implementing new features:

- **Centralized Implementation**: Implement the feature provider class in `commonMain`. There should be only one implementation per feature module. Do not create separate provider classes for different platforms.
- **Mandatory Inheritance**: Feature provider implementations (e.g., `FirebaseAnalyticsProvider`) must inherit from:
    - `KoinActionFeatureProvider`: Integrates the feature with Koin.
    - `FeaturePreview`: Enables the feature to be displayed and tested in the app's preview/debug dashboard.
    - The feature's API interface (e.g., `AnalyticsFeature`).
- **Platform Delegation**:
    - Move all platform-specific logic to `expect`/`actual` functions or properties.
    - These should be placed in a file named `<FeatureName>Platform.kt` (e.g., `FirebaseAnalyticsPlatform.kt`).
    - Use platform-specific file naming: `FirebaseAnalyticsPlatform.android.kt`, `FirebaseAnalyticsPlatform.ios.kt`, etc.
- **Availability Check**: Use `isSupported(): Boolean` as the standard naming in the platform file to check for platform support. The provider's `isAvailable()` should delegate to this.
- **Isolation**: When working on a feature, strictly avoid modifying files in unrelated feature modules.
