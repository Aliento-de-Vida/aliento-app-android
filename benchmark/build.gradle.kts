import deps.androidx.Test

plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.alientodevida.alientoapp.benchmark"
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation("androidx.test.ext:junit:${Test.junit}")
    implementation("androidx.test.espresso:espresso-core:${Test.espresso}")
    implementation("androidx.test.uiautomator:uiautomator:${Test.uiAutomator}")
    implementation("androidx.benchmark:benchmark-macro-junit4:${Test.benchmark}")
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enabled = it.buildType == "benchmark"
    }
}
