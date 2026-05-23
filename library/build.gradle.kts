import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.alexey-odintsov"
val artifact = "uicomponents"
version = "0.0.2"

kotlin {
    jvm()
    withSourcesJar(publish = false)

    androidLibrary {
        namespace = "io.github.alexey_odintsov.uicomponents"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(
                        JvmTarget.JVM_17
                    )
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.material.icons.extended)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlin.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
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