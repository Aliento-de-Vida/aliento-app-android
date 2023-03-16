@file:Suppress("UnstableApiUsage")

import deps.androidx.AndroidX
import deps.config
import deps.google.Firebase
import deps.google.Gson
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.androidx.Test
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(BasicPlugin.plugin)
    id(BuildPlugins.googleServices)
    id(BuildPlugins.safeArgs)
    id(BuildPlugins.crashlytics)
}

android {
    namespace = "com.alientodevida.alientoapp.app"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

android {
    namespace = "com.alientodevida.alientoapp.app"
    compileSdk = config.compileSdk
    buildToolsVersion = config.buildTools

    defaultConfig {
        applicationId = "com.alientodevida.app"
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
        val release by getting {
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(
                "String",
                "YOUTUBE_DEVELOPER_KEY",
                "\"AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0\"",
            )
            buildConfigField("String", "BASE_URL", "\"https://todoserver-peter.herokuapp.com\"")
        }
        val debug by getting {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField(
                "String",
                "YOUTUBE_DEVELOPER_KEY",
                "\"AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0\"",
            )
            buildConfigField("String", "BASE_URL", "\"https://todoserver-peter.herokuapp.com\"")
        }
        val benchmark by creating {
            initWith(getByName("release"))
            proguardFiles("benchmark-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
        }
    }
}

dependencies {
    implementation("androidx.profileinstaller:profileinstaller:${Test.profileinstaller}")

    // Modules
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
    implementation(
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}",
    )
    implementation("com.google.code.gson:gson:${Gson.lib}")
}
