
plugins {
    val kotlinVersion = libs.versions.kotlin
    java
    kotlin("jvm") version kotlinVersion
    application
    id("java")
}

application {
    mainClass.set("necasond.uno.Main")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

repositories {
    mavenCentral()
}
