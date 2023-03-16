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
        classpath("${BuildPlugins.ktlintGradlePlugin}:${deps.jlleitschuh.Ktlint.gradle}")
    }
}

subprojects {
    apply(plugin = BuildPlugins.ktlintPlugin)

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

    tasks.withType(org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask::class.java) {
        reportsOutputDirectory.set(
            rootProject.layout.projectDirectory.dir("tools/ktlint/ktlint/${project.name}-lint-report.txt")
        )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
