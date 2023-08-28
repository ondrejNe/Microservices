
plugins {
    val kotlinVersion = libs.versions.kotlin
    java
    kotlin("jvm") version kotlinVersion
    application
}

application {
    mainClass.set("necasond.dos.Main")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
