import deps.coil.Coil
import deps.androidx.Compose
import deps.androidx.AndroidX
import deps.google.Material
import deps.google.Accompanist
import deps.github.Glide
import deps.Java

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android {
    namespace = "com.alientodevida.alientoapp.designsystem"
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
    implementation(project(":core:analytics"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    // Material
    implementation("com.google.android.material:material:${Material.lib}")

    // Compose
    implementation("androidx.compose.animation:animation:${Compose.lib}")
    implementation("androidx.compose.ui:ui-tooling:${Compose.lib}")
    implementation("androidx.compose.runtime:runtime-livedata:${Compose.lib}")
    implementation("androidx.compose.material:material:${Compose.material}")
    implementation("androidx.compose.material:material-icons-extended:${Compose.material}")
    // Libraries using compose
    implementation("androidx.constraintlayout:constraintlayout-compose:${AndroidX.constraintLayoutCompose}")
    implementation("androidx.activity:activity-compose:${AndroidX.activityCompose}")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:${Accompanist.lib}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Accompanist.lib}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${Accompanist.lib}")

    // images
    implementation("com.github.bumptech.glide:glide:${Glide.lib}")
    kapt("com.github.bumptech.glide:compiler:${Glide.lib}")
    implementation("io.coil-kt:coil-compose:${Coil.lib}")
}