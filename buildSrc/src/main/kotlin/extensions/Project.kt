package extensions

import org.gradle.api.Action
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun org.gradle.api.Project.`java`(configure: Action<JavaPluginExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("java", configure)

fun org.gradle.api.Project.`kotlin`(configure: Action<KotlinAndroidProjectExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlin", configure)
