plugins {
    val kotlinVersion = libs.versions.kotlin
    java
    kotlin("jvm") version kotlinVersion
    application
}

val javaVersion: Int = libs.versions.java.get().toInt()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

kotlin {
    jvmToolchain(javaVersion)
}

application {
    mainClass.set("necasond.dos.Main")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        // We need this for Gradle optimization to work
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        // Naming the jar
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        // Manifest configuration
        manifest {
            attributes(
                mapOf("Main-Class" to application.mainClass)
            )
        }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }

    build {
        // Trigger fat jar creation during build
        dependsOn(fatJar)
    }
}
