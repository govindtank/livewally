#!/bin/bash
# Release build script for LiveWally
# Usage: ./build-release.sh [app-bundle|apk]

set -e

BUILD_TYPE=${1:-app-bundle}

echo "🚀 Building LiveWally for release..."

# Check for signing config
if [ ! -f "signing/signing-config.properties" ]; then
    echo "⚠️  Warning: signing/signing-config.properties not found"
    echo "   Copy signing/signing-config.properties.example and fill in your keystore details"
    echo "   Building unsigned release..."
    SIGNING_CMD=""
else
    echo "✅ Signing config found"
    SIGNING_CMD="-PsigningConfigFile=signing/signing-config.properties"
fi

# Build
if [ "$BUILD_TYPE" = "apk" ]; then
    echo "📦 Building release APK..."
    ./gradlew assembleRelease $SIGNING_CMD
    OUTPUT="app/build/outputs/apk/release/app-release.apk"
else
    echo "📦 Building release App Bundle..."
    ./gradlew bundleRelease $SIGNING_CMD
    OUTPUT="app/build/outputs/bundle/release/app-release.aab"
fi

echo ""
echo "✅ Build complete!"
echo "📁 Output: $OUTPUT"
echo ""
echo "Next steps:"
echo "1. Test the release build on a device"
echo "2. Upload to Google Play Console"
echo "3. Submit for review"
