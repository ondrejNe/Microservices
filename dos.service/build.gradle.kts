
plugins {
    val kotlinVersion = libs.versions.kotlin
    java
    kotlin("jvm") version kotlinVersion
    application
}

application {
    mainClass.set("necasond.dos.Main")
}