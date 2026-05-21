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
