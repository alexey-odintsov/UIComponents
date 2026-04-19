import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.vanniktech.mavenPublish)
    id("maven-publish")
}

group = "io.github.alexey-odintsov"
val artifact = "uicomponents"
version = "0.2.10"

kotlin {
    jvm()
    withSourcesJar(publish = false)

    androidLibrary {
        namespace = "io.github.alexey-odintsov.uicomponents"
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
                        JvmTarget.JVM_11
                    )
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
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

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/alexey-odintsov/uicomponents")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: findProperty("gpr.user") as String?
                password = System.getenv("GITHUB_TOKEN") ?: findProperty("gpr.key") as String?
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "alexey.odintsov.uicomponents.resources"
}