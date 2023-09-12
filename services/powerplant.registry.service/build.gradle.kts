plugins {
    // JVM
    java
    application
    kotlin("jvm") version libs.versions.kotlin
    // Spring
    kotlin("plugin.spring") version libs.versions.kotlinSpring
    alias(libs.plugins.springBootPlugin)
    alias(libs.plugins.springDependencyManagementPlugin)
}

// JVM plugins specifications
val javaVersion: Int = libs.versions.java.get().toInt()
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}
val mainClassPath: String = "org.necasond.Main"
application {
    mainClass.set(mainClassPath)
}
kotlin {
    jvmToolchain(javaVersion)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Spring
    implementation(libs.springBootStarterWeb)
    implementation(libs.springBootStarterValidation)
    implementation(libs.springBootActuator)
    implementation(libs.springBootDataRest)
    implementation(libs.springBootJacksonKotlin)
    implementation(libs.kotlinReflections)
    implementation(libs.springBootKotlinStdLib)
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        group = "necasond"
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
