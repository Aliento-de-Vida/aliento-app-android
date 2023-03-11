import deps.androidx.Room
import deps.google.Gson
import deps.google.Hilt
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.Java

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinSerialization)
    id(BuildPlugins.kotlinParcelize)
}

android {
    namespace = "com.alientodevida.alientoapp.domain"
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Java.sourceCompatibility
        targetCompatibility = Java.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Java.jvmTarget
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.lib}")

    // Hilt  TODO plugin ?
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")

    // Architecture components
    implementation("androidx.room:room-runtime:${Room.lib}")
    kapt("androidx.room:room-compiler:${Room.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}")
    implementation("com.google.code.gson:gson:${Gson.lib}")
}