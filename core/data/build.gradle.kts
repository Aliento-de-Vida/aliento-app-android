import deps.androidx.AndroidX
import deps.google.Gson
import deps.google.Hilt
import deps.jakewharton.RetrofitCoroutinesAdapter
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.square.OkHttp
import deps.square.Retrofit

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hiltAndroid)
}

android {
    namespace = "com.alientodevida.alientoapp.core.data"
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = deps.Java.sourceCompatibility
        targetCompatibility = deps.Java.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = deps.Java.jvmTarget
    }
}

dependencies {
    // Modules
    implementation(project(":core:domain"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.lib}")
    implementation("androidx.core:core-ktx:${Kotlin.ktx}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")

    // Architecture components
    implementation("androidx.datastore:datastore-preferences:${AndroidX.datastore}")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:${Retrofit.lib}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${RetrofitCoroutinesAdapter.lib}")
    implementation("com.squareup.okhttp3:logging-interceptor:${OkHttp.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}")
    implementation("com.google.code.gson:gson:${Gson.lib}")
}