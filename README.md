# CutNow SDK Sample Project

This is a sample Android application demonstrating how to integrate and use the `cutNowSDK`.

## Integration

The SDK is included as an AAR file located in `app/libs/cutNowSDK-release.aar`.

### Gradle Configuration

The `app/build.gradle` file includes the AAR and its required dependencies:

```gradle
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.aar'])
    
    // Transitive dependencies
    implementation "androidx.appcompat:appcompat:1.6.1"
    implementation "androidx.core:core-ktx:1.12.0"
    // ... other dependencies
}
```

The `settings.gradle` file includes the JitPack repository:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url "https://jitpack.io" }
        google()
        mavenCentral()
    }
}
```

## How to Run

1. Open this project in Android Studio.
2. Sync Gradle.
3. Run the `app` module on an emulator or physical device.

## License

Copyright (c) 2026 AndroPlaza
