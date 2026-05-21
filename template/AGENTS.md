# Template Project Context

This is a multiplatform Compose project template. It is designed to be highly modular and configurable by the `processor`.

## Project Structure

- `client/`: The main application module. Contains platform-specific code and app-level configuration.
- `shared/`: Logic shared between features and the app.
    - `shared:domain`: Pure business logic, use cases, and models.
    - `shared:data`: Implementation of data sources and repositories.
    - `shared:presentation`: Common UI components, design system, and base ViewModels.
- `feature/`: Self-contained feature modules. Each feature is typically split into:
    - `feature:{id}:client:api`: Public interface of the feature.
    - `feature:{id}:client:{impl}`: Specific implementation (e.g., `stub`, `real`, `supabase`).
- `server/`: Ktor-based backend module.

## Architectural Guidelines

- **Clean Architecture**: Follow the separation of layers: `presentation` -> `domain` <- `data`.
- **MVVM**: Use Jetpack ViewModel for UI logic. States are typically managed via `StateFlow`.
- **Dependency Injection**: Use **Koin**. Modules are defined in `AppConfig.kt` and feature-specific modules.
- **Compose Multiplatform**: All UI should be written in Compose, prioritizing `commonMain`.

## Feature Development Guidelines

- **Encapsulation & Naming**:
    - Avoid feature-specific prefixes (e.g., `Sideload`) for internal classes and files. Use generic names (e.g., `UpdateRepository`).
    - Encapsulate each screen or flow into its own package containing its `Route`, `ViewModel`, `Content`, and `UiState`.
    - Place the external contract (e.g., `Provider`, `State`, `StateResolver`) in the root package of the feature module.
    - Mark all classes, interfaces, and objects within `domain`, `data`, and `presentation` packages as `internal`. Only the root package should contain public declarations.
- **Feature Provider (`BaseFeatureProvider`)**:
    - `onProvideContent` should resolve the initial feature state in the background and navigate to the appropriate route if needed, rather than displaying UI directly.
    - Avoid triggering feature-specific logic from global ViewModels (like `AppViewModel`); instead, handle it within the feature provider or its registered components.
    - Register all routes (screens and dialogs) in the navigation graph within `onProvideNavigation`.
    - Use `getMethods` to expose manual feature actions (e.g., `checkForUpdates`) to the rest of the app.
- **ViewModel & State Management**:
    - Inherit ViewModels from `BaseViewModel` and utilize `ViewState` (`uiState`, `uiEvent`) for standardized state handling.
    - Use `tryCatch` in ViewModels for consistent error management.
    - Inject ViewModels using `koinFeatureViewModel()` to maintain proper feature scoping.
- **Data & Domain Layers**:
    - **Use Cases**: All communication between ViewModels and Repositories MUST go through UseCases.
    - **Repositories**: Abstract all data access and persistent storage. Do not use DataSources directly in ViewModels.
    - **State Resolvers**: Use a decorator pattern for resolvers (e.g., `PersistentUpdateStateResolver`) if you need to check local persistent state before performing remote operations.
- **String Resources**:
    - Extract ALL user-facing strings into `composeResources/values/strings.xml`.
    - To access resources use the official Compose Resources API:
        - import `org.jetbrains.compose.resources.stringResource` and use `stringResource(Res.string.name)` in Composables.
        - import `org.jetbrains.compose.resources.getString` and use `getString(Res.string.name)` in ViewModels (if in a suspending context).
    - **Resource Imports**:
        - Always use the full package path for the generated `Res` class: `import {full.package.name}.generated.resources.Res` (e.g., `import template.feature.update.client.sideload.generated.resources.Res`).
        - For every resource used via `Res.string.name`, you MUST add an explicit import for that resource: `import {full.package.name}.generated.resources.{name}`.
    - Avoid hardcoding user-facing strings.

## CRITICAL: Marker Usage

The `template` is processed by an external engine. You **MUST** use markers for any conditional code:

- **Line markers**: `// {marker.id}` at the end of a line.
- **Block markers**:
    ```kotlin
    // {marker.id}
    codeBlock()
    // {marker.id}
    ```
- **Gradle markers**: Use curly brace markers in `build.gradle.kts` for dependencies and configuration.

### Common Markers
- `{platform.android}`, `{platform.ios}`, `{platform.js}`, `{platform.wasmJs}`, `{platform.jvm}`: Platform-specific inclusions.
- `{feature.{id}}`: Feature-specific inclusions.
- `{kotli.namespace}`, `{kotli.name}`: Placeholders for project generation.

## Best Practices for AI Agents

1. **Check for markers**: Before adding or removing code, check if there are surrounding markers that you should respect or replicate.
2. **Modularize**: When adding new functionality, consider if it should be a new `feature` or part of `shared`.
3. **Consistency**: Follow the existing naming conventions (e.g., `Ds` prefix for Design System components).
4. **Platform-Awareness**: Use `expect/actual` or platform-specific source sets only when necessary. Prefer `commonMain`.
