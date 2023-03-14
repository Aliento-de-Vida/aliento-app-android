package extensions

import com.android.build.gradle.BaseExtension
import deps.androidx.Compose
import deps.config

fun BaseExtension.defaultConfig() {
    compileSdkVersion(config.compileSdk)
    buildToolsVersion = config.buildTools
    defaultConfig {
        minSdk = config.minSdk
        targetSdk = config.targetSdk
    }
}

fun BaseExtension.compileOptions() {
    compileOptions {
        sourceCompatibility = deps.Java.sourceCompatibility
        targetCompatibility = deps.Java.targetCompatibility
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.compiler
    }
}

fun BaseExtension.buildTypes() {
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isTestCoverageEnabled = true
            isPseudoLocalesEnabled = true
            isJniDebuggable = true
            isRenderscriptDebuggable = true
            renderscriptOptimLevel = 3
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isDebuggable = false
            isTestCoverageEnabled = false
            isPseudoLocalesEnabled = false
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            renderscriptOptimLevel = 3
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun BaseExtension.packagingOptions() {
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/dependencies"
            excludes += "META-INF/dependencies.txt"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/DEPENDENCIES.txt"
            excludes += "META-INF/license"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/notice"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}
