plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.prayer"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}
