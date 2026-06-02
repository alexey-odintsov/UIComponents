plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":library"))
            implementation(libs.jetbrains.androidx.lifecycle.viewmodelCompose)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.jetbrains.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "alexey.odintsov.uicomponents.testapp.MainKt"
    }
}
