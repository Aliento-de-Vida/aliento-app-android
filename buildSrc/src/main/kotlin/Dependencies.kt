import deps.jetbrains.Kotlin

object BuildPlugins {
    const val androidApplication = "com.android.application"
    const val googleServices = "com.google.gms.google-services"
    const val safeArgs = "androidx.navigation.safeargs"
    const val crashlytics = "com.google.firebase.crashlytics"

    const val androidLibrary = "com.android.library"
    const val hiltAndroid = "dagger.hilt.android.plugin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinSerialization = "org.jetbrains.kotlin.plugin.serialization"
    const val kotlinParcelize = "kotlin-parcelize"

    const val gradleVersion = "8.0.1"
    const val hiltPluginVersion = "2.44"
    const val navigationVersion = "2.4.1"
    const val gogleServicesVersion = "4.3.15"
    const val crashlyticsVersion = "2.9.4"

    const val gradle = "com.android.tools.build:gradle:$gradleVersion"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.lib}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltPluginVersion"
    const val navigationPlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
    const val googleServicesClassPath = "com.google.gms:google-services:$gogleServicesVersion"
    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.lib}"
    const val crashlyticsPlugin =
        "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsVersion"
    const val ktlintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle"
    const val ktlintPlugin = "org.jlleitschuh.gradle.ktlint"
}
