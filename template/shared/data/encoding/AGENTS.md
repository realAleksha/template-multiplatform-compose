# Encoding Module

This module provides strategies for encoding and decoding objects to and from strings.

## Key Components

- `EncodingStrategy`: Base interface for all encoding strategies.
- `JsonEncodingStrategy`: Implementation using `kotlinx-serialization`.
- `ByteArrayEncodingStrategy`: Strategy for handling raw byte arrays.
- `DefaultEncodingStrategy`: A simple string-based strategy.

## Usage

When you need to store complex objects in settings or send them over the network, use an `EncodingStrategy`.
Refer to `EncodingStrategy.json()` for the most common use case.
