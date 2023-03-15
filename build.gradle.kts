buildscript {
    repositories {
        google()
        mavenCentral()
        repositories { maven("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath(BuildPlugins.gradle)
        classpath(BuildPlugins.kotlinGradle)

        classpath(BuildPlugins.hiltPlugin)
        classpath(BuildPlugins.navigationPlugin)
        classpath(BuildPlugins.googleServicesClassPath)
        classpath(BuildPlugins.serialization)
        classpath(BuildPlugins.crashlyticsPlugin)
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.3.1")
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(false)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
