plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.admin"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}