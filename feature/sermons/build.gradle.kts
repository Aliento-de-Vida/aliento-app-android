import deps.androidx.AndroidX
import deps.square.OkHttp

plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.sermons"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation("androidx.room:room-runtime:${AndroidX.room}")
    kapt("androidx.room:room-compiler:${AndroidX.room}")
    implementation("com.squareup.okhttp3:logging-interceptor:${OkHttp.lib}")
}