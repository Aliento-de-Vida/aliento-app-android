import com.android.build.gradle.BaseExtension
import deps.Java
import deps.androidx.AndroidX
import deps.androidx.Compose
import deps.github.Stfalcon
import deps.google.Hilt
import deps.jakewharton.RetrofitCoroutinesAdapter
import deps.jakewharton.RetrofitSerializationConverter
import deps.jetbrains.Kotlin
import deps.square.Retrofit
import extensions.buildTypes
import extensions.compileOptions
import extensions.defaultConfig
import extensions.java
import extensions.kotlin
import extensions.packagingOptions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.fileTree
import org.gradle.kotlin.dsl.project

class FeaturePlugin : Plugin<Project> {

    companion object {
        const val plugin = "apply-feature-plugin"
    }

    override fun apply(target: Project): Unit = target.run {
        plugins.apply(BuildPlugins.androidLibrary)
        plugins.apply(BuildPlugins.kotlinAndroid)
        plugins.apply(BuildPlugins.kotlinKapt)
        plugins.apply(BuildPlugins.hiltAndroid)
        plugins.apply(BuildPlugins.kotlinSerialization)
        plugins.apply(BuildPlugins.kotlinParcelize)

        kotlin {
            jvmToolchain {
                languageVersion.set(JavaLanguageVersion.of(Java.jvmTarget))
            }
        }
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(Java.jvmTarget))
            }
        }

        extensions.findByType(BaseExtension::class.java)?.configure()

        dependencies()
    }
}

private fun BaseExtension.configure() {
    defaultConfig()
    compileOptions()
    buildTypes()
    packagingOptions()
}

private fun Project.dependencies() {
    this.dependencies {
        add("implementation", fileTree("dir" to "libs", "include" to listOf("*.jar")))

        add("implementation", project(":core:domain"))
        add("implementation", project(":core:data"))
        add("implementation", project(":core:analytics"))
        add("implementation", project(":core:presentation"))
        add("implementation", project(":core:common"))
        add("implementation", project(":core:designsystem"))

        // Hilt
        add("implementation", "com.google.dagger:hilt-android:${Hilt.lib}")
        add("kapt", "com.google.dagger:hilt-compiler:${Hilt.lib}")
        add("implementation", "androidx.hilt:hilt-navigation-compose:${AndroidX.hiltNavigation}")

        // Architecture components
        add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:${AndroidX.lifecycle}")
        add("implementation", "androidx.lifecycle:lifecycle-livedata-ktx:${AndroidX.lifecycle}")
        add("implementation", "androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidX.lifecycle}")

        // Compose
        add("implementation", "androidx.compose.animation:animation:${Compose.lib}")
        add("implementation", "androidx.compose.ui:ui-tooling:${Compose.lib}")
        add("implementation", "androidx.compose.runtime:runtime-livedata:${Compose.lib}")
        add("implementation", "androidx.compose.material:material:${Compose.material}")
        add(
            "implementation",
            "androidx.navigation:navigation-compose:${AndroidX.composeNavigation}",
        )

        // Networking
        add("implementation", "com.squareup.retrofit2:retrofit:${Retrofit.lib}")
        add(
            "implementation",
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${RetrofitCoroutinesAdapter.lib}",
        )

        // images
        add("implementation", "com.github.bumptech.glide:glide:${deps.github.Glide.lib}")
        add("kapt", "com.github.bumptech.glide:compiler:${deps.github.Glide.lib}")
        add("implementation", "io.coil-kt:coil-compose:${deps.coil.Coil.lib}")
        add("implementation", "com.github.stfalcon-studio:StfalconImageViewer:${Stfalcon.lib}")

        // Serialization
        add(
            "implementation",
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.serialization}",
        )
        add(
            "implementation",
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${RetrofitSerializationConverter.lib}",
        )
    }
}
