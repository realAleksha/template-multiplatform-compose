# Processor Module Context

The `processor` module is responsible for transforming the `template` based on selected features. It uses the `kotli-engine` to perform these transformations.

## Core Concepts

- **FeatureProvider**: Defines a group of related features. It registers `FeatureProcessor` instances.
- **FeatureProcessor**: Implements the logic for a specific feature. It defines how to "apply" the feature (include it) and how to "remove" it (if it's optional).
- **TemplateState**: Carries the current state of the transformation process, including the files being processed.
- **Rules.kt**: Centralizes path patterns used to find files in the template.

## Workflow for Adding a Feature

1. **Identify Template Changes**: Determine what code needs to be added to or removed from the `template`.
2. **Add Markers to Template**: Wrap the conditional code in the `template` with markers (e.g., `// {feature.id}`).
3. **Define Rule**: If the feature affects new file types or specific paths, add a rule in `Rules.kt`.
4. **Create Processor**: Implement a `FeatureProcessor` (or extend `UserFeatureProcessor`) that uses `CleanupMarkedBlock` in `doApply` and `RemoveMarkedBlock` in `doRemove`.
5. **Register Processor**: Add the processor to the appropriate `FeatureProvider`.

## Transformation Techniques

- **Marker-based**: The most common method. Uses curly brace markers in comments.
    - `CleanupMarkedBlock("{id}")`: Removes the markers but keeps the content.
    - `RemoveMarkedBlock("{id}")`: Removes both the markers and the content.
- **File-based**: Some processors add or remove entire files based on the feature's presence.
- **Text Replacement**: Replacing placeholders like `{kotli.name}` with the actual project name.

## Guidelines

- **Idempotency**: Processors should be designed to be safe to run, though typically they run once during generation.
- **Separation of Concerns**: Each processor should handle only its specific feature logic.
- **Reuse Rules**: Use existing patterns from `Rules.kt` whenever possible.
