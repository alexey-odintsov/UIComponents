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

### Publish to Maven Local
To publish the library to your local Maven repository (usually located at `~/.m2/repository`), run:
```bash
./gradlew :library:publishToMavenLocal
```

### Publish to Maven Central
The project uses the `vanniktech.mavenPublish` plugin. To publish to Maven Central, run:
```bash
./gradlew :library:publishAllPublicationsToMavenCentralRepository
```
Make sure you have your Sonatype credentials and GPG signing keys configured in your `gradle.properties` or environment variables.

### Publish to GitHub Packages
To publish the library to GitHub Packages, run:
```bash
./gradlew :library:publishAllPublicationsToGitHubPackagesRepository
```
This requires `GITHUB_ACTOR` and `GITHUB_TOKEN` environment variables to be set.
