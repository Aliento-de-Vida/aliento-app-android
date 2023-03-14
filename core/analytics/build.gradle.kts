import deps.google.Firebase

plugins {
    id(CoreLibPlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.core.analytics"
}

dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${Firebase.lib}"))
    implementation("com.google.firebase:firebase-analytics-ktx")
}