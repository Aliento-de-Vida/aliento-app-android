import deps.Java
import deps.androidx.AndroidX
import deps.androidx.Compose
import deps.github.Stfalcon
import deps.google.Hilt
import deps.jakewharton.RetrofitCoroutinesAdapter
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.square.Retrofit

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hiltAndroid)
}

android {
    namespace = "com.alientodevida.alientoapp.church"
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.compiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Modules
    implementation(project(":core:domain"))
    implementation(project(":core:analytics"))
    implementation(project(":core:presentation"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    // Hilt
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")
    implementation("androidx.hilt:hilt-navigation-compose:${AndroidX.hiltNavigation}")

    // Architecture components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${AndroidX.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${AndroidX.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidX.lifecycle}")

    // Compose
    implementation("androidx.compose.animation:animation:${Compose.lib}")
    implementation("androidx.compose.ui:ui-tooling:${Compose.lib}")
    implementation("androidx.compose.runtime:runtime-livedata:${Compose.lib}")
    implementation("androidx.compose.material:material:${Compose.material}")
    implementation("androidx.navigation:navigation-compose:${AndroidX.composeNavigation}")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:${Retrofit.lib}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${RetrofitCoroutinesAdapter.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}")
}