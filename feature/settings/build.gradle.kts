import deps.google.Firebase

plugins {
    id(FeaturePlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.settings"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${Firebase.lib}"))
    implementation("com.google.firebase:firebase-messaging-ktx")
}
