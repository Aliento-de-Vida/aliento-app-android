buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.gradle)
        classpath(BuildPlugins.kotlinGradle)

        classpath(BuildPlugins.hiltPlugin)
        classpath(BuildPlugins.navigationPlugin)
        classpath(BuildPlugins.gogleServices)
        classpath(BuildPlugins.serialization)
        classpath(BuildPlugins.crashlyticsPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}