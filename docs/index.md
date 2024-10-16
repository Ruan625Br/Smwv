# Overview

## What is Smwv?

**Smwv** (SA-MP Mobile WebView) is a bidirectional Kotlin library that embeds WebView into SA-MP clients, allowing you to build interfaces using frameworks like React, Vue, and others.

### How does it work?

The communication flow in Smwv operates in a bidirectional manner:

1. **Server to Smwv**: The server sends data or events to Smwv.
2. **Smwv to WebView**: Smwv relays this information to the embedded WebView, allowing it to update or change the UI accordingly.
3. **WebView to Smwv**: User interactions or events in the WebView can be captured and sent back to Smwv.
4. **Smwv to Server**: Finally, Smwv forwards these events back to the server for processing.
