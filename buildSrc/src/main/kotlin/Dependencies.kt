import org.gradle.api.JavaVersion
import deps.jetbrains.Kotlin

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "30.0.3"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.lib}"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val hiltAndroid = "dagger.hilt.android.plugin"

    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinSerialization = "org.jetbrains.kotlin.plugin.serialization"
    const val kotlinParcelize = "kotlin-parcelize"
}

object AndroidSdk {
    const val min = 21
    const val compile = 33
    const val target = 33
}

object Libraries {
    private object Versions {
        const val ktx = "1.9.0"
    }

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
}