# HTTP Module

This module provides the base HTTP client configuration using Ktor.

## Key Components

- `HttpSource`: A data source that wraps a Ktor `HttpClient`. It is pre-configured with:
    - Content Negotiation (JSON)
    - Logging (Headers)
    - WebSockets support
    - Redirection handling
    - Timeouts
    - Optional retries

## Usage

Extend `HttpSource` or use it directly when making network requests.
The `client` property provides access to the underlying Ktor `HttpClient`.
