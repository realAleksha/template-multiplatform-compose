# Data Layer Context

The data layer is responsible for providing data to the domain layer. It consists of multiple data source modules, each serving a specific purpose.

## Common Architecture

- **Contract/Implementation Separation**: Most data sources are split into a contract module (e.g., `:shared:data:settings`) and one or more implementation modules (e.g., `:shared:data:settings-datastore`).
- **Common Logic**: Base classes (like `DataSource`) and miscellaneous utilities are in `:shared:data:common`.
- **Core Functionality**: Encoding and HTTP functionality are separated into `:shared:data:encoding` and `:shared:data:http` respectively.
- **Package Naming**: All data source modules follow the package pattern `shared.data.<name>`.

## Guidelines for AI

- **Depend on Contracts**: When a feature needs a data source, it should only depend on the contract module.
- **Implementation in Client**: Implementation modules should only be added as dependencies in the final client application (e.g., `:client`).
