import deps.google.Hilt

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android {
    namespace = "com.alientodevida.alientoapp.core.common"
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

    // Hilt
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")
}