import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "io.github.alexey-odintsov"
val artifact = "uicomponents"
version = "0.0.3"

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    withSourcesJar(publish = true)

    androidLibrary {
        namespace = "io.github.alexey_odintsov.uicomponents"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.foundation)
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.material.icons.extended)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), artifact, version.toString())

    pom {
        name = "KMP UI components library"
        description = "UI components library."
        inceptionYear = "2025"
        url = "https://github.com/alexey-odintsov/UIComponents"
        licenses {
            license {
                name = "MIT License"
            }
        }
        developers {
            developer {
                id = "alexey-odintsov"
                name = "Alexey Odintsov"
                url = "https://github.com/alexey-odintsov/"
            }
        }
        scm {
            url = "https://github.com/alexey-odintsov/UIComponents"
            connection = "scm:git:git://github.com/alexey-odintsov/UIComponents.git"
            developerConnection = "scm:git:ssh://git@github.com/alexey-odintsov/UIComponents.git"
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "alexey.odintsov.uicomponents.resources"
}