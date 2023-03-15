import deps.androidx.Room
import deps.google.Gson
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin

plugins {
    id(CoreLibPlugin.plugin)
    id(BuildPlugins.kotlinSerialization)
    id(BuildPlugins.kotlinParcelize)
}

android {
    namespace = "com.alientodevida.alientoapp.domain"
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.lib}")

    // Architecture components
    implementation("androidx.room:room-runtime:${Room.lib}")
    kapt("androidx.room:room-compiler:${Room.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation(
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}",
    )
    implementation("com.google.code.gson:gson:${Gson.lib}")
}
