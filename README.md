# UIComponents

KMP library for Desktop, Android, and web apps (Kotlin/JS and Kotlin/Wasm browser targets).

Web support is available in the source build; previously published versions are unchanged.

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

## Web demo

The `web-test-app` module runs on both browser targets:

```shell
./gradlew :web-test-app:jsBrowserDevelopmentRun
./gradlew :web-test-app:wasmJsBrowserDevelopmentRun
```

Run one command at a time and open the local URL printed by the development server.
The demo includes theme switching, Table/Table2 selection and resizing, row highlighting,
text wrapping, and a status bar. Buttons, inputs, and dialogs use Material controls;
the library's JVM-only controls and native file dialogs are not included.

Build browser bundles with `:web-test-app:jsBrowserDistribution` or
`:web-test-app:wasmJsBrowserDistribution`.

## Publishing

See [PUBLISHING.md](PUBLISHING.md) for instructions on how to publish the library.
