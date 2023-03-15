import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
        apiVersion = "1.8"
        languageVersion = "1.8"
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    implementation("com.squareup:javapoet:1.13.0")
}

gradlePlugin {
    plugins {
        create("apply-feature-plugin") {
            id = "apply-feature-plugin"
            implementationClass = "FeaturePlugin"
        }

        create("apply-core-plugin") {
            id = "apply-core-plugin"
            implementationClass = "CoreLibPlugin"
        }
    }
}
