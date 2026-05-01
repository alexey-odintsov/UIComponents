# UIComponents

KMP library for Desktop and Android apps.

See [COMPONENTS.md](COMPONENTS.md) for a detailed list of available components and their platform support.

## Usage

### Kotlin Multiplatform

Add the dependency to your `commonMain` source set in `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.alexey-odintsov:uicomponents:0.0.1")
        }
    }
}
```

### Android

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.alexey-odintsov:uicomponents-android:0.0.1")
}
```

## Publishing

See [PUBLISHING.md](PUBLISHING.md) for instructions on how to publish the library.
