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
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.7")
//    implementation("org.slf4j:slf4j-core:2.0.7")
//    implementation("org.slf4j:slf4j-impl:2.0.7")
}
