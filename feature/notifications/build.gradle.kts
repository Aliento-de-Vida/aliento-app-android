plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.notifications"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}