plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.church"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}