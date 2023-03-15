plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.gallery"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}
