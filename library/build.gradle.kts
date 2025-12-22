import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("maven-publish")
}

val libraryGroup = "alexey.odintsov"
val libraryArtifact = "uicomponents"
val libraryVersion = "0.1.6"

kotlin {
    jvm()
    withSourcesJar(publish = false)

//    androidLibrary {
//        namespace = "alexey.odintsov.kmp.uicomponents"
//        compileSdk = libs.versions.android.compileSdk.get().toInt()
//        minSdk = libs.versions.android.minSdk.get().toInt()
//
//        withJava() // enable java compilation support
//        withHostTestBuilder {}.configure {}
//        withDeviceTestBuilder {
//            sourceSetTreeName = "test"
//        }
//
//        compilations.configureEach {
//            compileTaskProvider.configure {
//                compilerOptions {
//                    jvmTarget.set(
//                        JvmTarget.JVM_11
//                    )
//                }
//            }
//        }
//    }

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

//if (!isCiBuild) {
//    mavenPublishing {
//        publishToMavenCentral()
////        signAllPublications()
//        coordinates(libraryGroup, libraryArtifact, libraryVersion)
//
//        pom {
//            name = "UIComponents"
//            description = "A library."
//            inceptionYear = "2025"
//        }
//    }
//} else {
    publishing {
        publications {
            create<MavenPublication>("gpr") {
                from(components["kotlin"])
                groupId = libraryGroup
                artifactId = libraryArtifact
                version = libraryVersion
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/alexey-odintsov/uicomponents")
                credentials {
                    username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
//}

compose.resources {
    publicResClass = true
    packageOfResClass = "alexey.odintsov.kmp.uicomponents.resources"
}