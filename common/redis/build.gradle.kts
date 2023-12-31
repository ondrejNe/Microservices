plugins {
    // JVM
    java
    kotlin("jvm") version libs.versions.kotlin
}

// JVM plugins specifications
val javaVersion: Int = libs.versions.java.get().toInt()
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
kotlin {
    jvmToolchain(javaVersion)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Kotlin
    implementation(libs.kotlinCoroutines)
    implementation(libs.kotlinCoroutinesJdk8)
    implementation(libs.kotlinCoroutinesMdc)
    implementation(libs.kotlinReflections)
    // Redis
    implementation(libs.kreds)
}
