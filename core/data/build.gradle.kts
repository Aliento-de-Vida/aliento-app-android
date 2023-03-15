import deps.androidx.AndroidX
import deps.google.Gson
import deps.google.Hilt
import deps.jakewharton.RetrofitCoroutinesAdapter
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.square.OkHttp
import deps.square.Retrofit

plugins {
    id(CoreLibPlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.core.data"
}

dependencies {
    // Modules
    implementation(project(":core:domain"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.lib}")
    implementation("androidx.core:core-ktx:${Kotlin.ktx}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Hilt.lib}")
    kapt("com.google.dagger:hilt-compiler:${Hilt.lib}")

    // Architecture components
    implementation("androidx.datastore:datastore-preferences:${AndroidX.datastore}")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:${Retrofit.lib}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${RetrofitCoroutinesAdapter.lib}")
    implementation("com.squareup.okhttp3:logging-interceptor:${OkHttp.lib}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}")
    implementation(
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}",
    )
    implementation("com.google.code.gson:gson:${Gson.lib}")
}
