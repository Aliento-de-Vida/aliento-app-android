import deps.Java
import deps.androidx.AndroidX
import deps.androidx.Compose
import deps.google.Firebase
import deps.google.Gson
import deps.google.Hilt
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import java.io.FileInputStream
import java.util.*
import deps.config

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.googleServices)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hiltAndroid)
    id(BuildPlugins.safeArgs)
    id(BuildPlugins.kotlinSerialization)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.crashlytics)
}

android {
    namespace = "com.alientodevida.alientoapp.app"
    compileSdk = config.compileSdk
    buildToolsVersion = config.buildTools

    defaultConfig {
        applicationId = "com.alientodevida.app"
        minSdk = config.minSdk
        targetSdk = config.targetSdk
        versionCode = config.versionCode
        versionName = config.versionName
    }

    signingConfigs {
        create("release") {
            val props = Properties().apply {
                load(FileInputStream(File(rootProject.rootDir, "keystore.properties")))
            }
            storeFile = File(props.getProperty("storeFile"))
            storePassword = props.getProperty("storePassword")
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyPassword")
        }
        getByName("debug") {

        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "YOUTUBE_DEVELOPER_KEY", "\"AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0\"")
            buildConfigField("String", "BASE_URL", "\"https://todoserver-peter.herokuapp.com\"")
        }
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String", "YOUTUBE_DEVELOPER_KEY", "\"AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0\"")
            buildConfigField("String", "BASE_URL", "\"https://todoserver-peter.herokuapp.com\"")
        }
    }

    compileOptions {
        sourceCompatibility = Java.sourceCompatibility
        targetCompatibility = Java.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Java.jvmTarget
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(Java.jvmTarget))
        }
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
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":feature:gallery"))
    implementation(project(":feature:campus"))
    implementation(project(":feature:sermons"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:notifications"))
    implementation(project(":feature:prayer"))
    implementation(project(":feature:admin"))
    implementation(project(":feature:church"))
    implementation(project(":feature:donations"))
    implementation(project(":feature:home"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Kotlin.coroutines}")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.lib}")
    implementation("androidx.core:core-ktx:${Kotlin.ktx}")

    // Appcompat
    implementation("androidx.appcompat:appcompat:${AndroidX.appCompat}")

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
    // Libraries using compose
    implementation("androidx.constraintlayout:constraintlayout-compose:${AndroidX.constraintLayoutCompose}")
    implementation("androidx.activity:activity-compose:${AndroidX.activityCompose}")
    implementation("io.coil-kt:coil-compose:${deps.coil.Coil.lib}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${Firebase.lib}"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}")
    implementation("com.google.code.gson:gson:${Gson.lib}")
}