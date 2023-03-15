plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.campus"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}
