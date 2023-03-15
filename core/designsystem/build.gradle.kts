import deps.androidx.AndroidX
import deps.androidx.Compose
import deps.coil.Coil
import deps.github.Glide
import deps.google.Accompanist
import deps.google.Material

plugins {
    id(CoreLibPlugin.plugin)
}

android {
    namespace = "com.alientodevida.alientoapp.designsystem"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    // Modules
    implementation(project(":core:analytics"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    // Material
    implementation("com.google.android.material:material:${Material.lib}")

    // Compose
    implementation("androidx.compose.animation:animation:${Compose.lib}")
    implementation("androidx.compose.ui:ui-tooling:${Compose.lib}")
    implementation("androidx.compose.runtime:runtime-livedata:${Compose.lib}")
    implementation("androidx.compose.material:material:${Compose.material}")
    implementation("androidx.compose.material:material-icons-extended:${Compose.material}")
    // Libraries using compose
    implementation("androidx.constraintlayout:constraintlayout-compose:${AndroidX.constraintLayoutCompose}")
    implementation("androidx.activity:activity-compose:${AndroidX.activityCompose}")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:${Accompanist.lib}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Accompanist.lib}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${Accompanist.lib}")

    // images
    implementation("com.github.bumptech.glide:glide:${Glide.lib}")
    kapt("com.github.bumptech.glide:compiler:${Glide.lib}")
    implementation("io.coil-kt:coil-compose:${Coil.lib}")
}
