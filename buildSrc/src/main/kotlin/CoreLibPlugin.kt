import com.android.build.gradle.BaseExtension
import deps.Java
import deps.androidx.AndroidX
import deps.google.Hilt
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

class CoreLibPlugin : Plugin<Project> {

    companion object {
        const val plugin = "apply-core-plugin"
    }

    override fun apply(target: Project): Unit = target.run {
        plugins.apply(BuildPlugins.androidLibrary)
        plugins.apply(BuildPlugins.kotlinAndroid)
        plugins.apply(BuildPlugins.kotlinKapt)
        plugins.apply(BuildPlugins.hiltAndroid)

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

        // Hilt
        add("implementation", "com.google.dagger:hilt-android:${Hilt.lib}")
        add("kapt", "com.google.dagger:hilt-compiler:${Hilt.lib}")
        add("implementation", "androidx.hilt:hilt-navigation-compose:${AndroidX.hiltNavigation}")
    }
}