// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}
plugins {
    val kotlin = "2.0.0"
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version kotlin apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    // Add the Compose Compiler Gradle plugin, the version matches the Kotlin plugin
    id("org.jetbrains.kotlin.plugin.compose") version kotlin apply false
}