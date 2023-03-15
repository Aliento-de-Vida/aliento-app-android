plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.donations"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}
