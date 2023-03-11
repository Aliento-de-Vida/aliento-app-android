import deps.Java
import deps.androidx.AndroidX
import deps.androidx.Compose
import deps.google.Firebase
import deps.google.Hilt
import deps.jetbrains.Kotlin

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hiltAndroid)
}

android {
    namespace = "com.alientodevida.alientoapp.ui"
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(":core:analytics"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    // Hilt
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")
    implementation("androidx.hilt:hilt-navigation-compose:${AndroidX.hiltNavigation}")

    // Appcompat
    implementation("androidx.appcompat:appcompat:${AndroidX.appCompat}")

    // Architecture components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${AndroidX.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${AndroidX.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidX.lifecycle}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${Firebase.lib}"))
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Compose
    implementation("androidx.compose.animation:animation:${Compose.lib}")
    implementation("androidx.compose.ui:ui-tooling:${Compose.lib}")
    implementation("androidx.compose.runtime:runtime-livedata:${Compose.lib}")
    implementation("androidx.compose.material:material:${Compose.material}")
    implementation("androidx.compose.material:material-icons-extended:${Compose.material}")
    implementation("androidx.navigation:navigation-compose:${AndroidX.composeNavigation}")

    // images
    implementation("com.github.bumptech.glide:glide:${deps.github.Glide.lib}")
    kapt("com.github.bumptech.glide:compiler:${deps.github.Glide.lib}")
    implementation("io.coil-kt:coil-compose:${deps.coil.Coil.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")

}