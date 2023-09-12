import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    val kotlinVersion = libs.versions.kotlin
    java
    kotlin("jvm") version kotlinVersion
    application
    id("com.bmuschko.docker-remote-api") version libs.versions.dockerRemoteApi
//    id("com.bmuschko.docker-java-application") version libs.versions.dockerJavaApplication
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
    mainClass.set("necasond.uno.Main")
}

docker {

}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.create("createDockerfile", Dockerfile::class) {
    group = "necasond"
    from("openjdk:17-jdk")
    copyFile("/libs/uno.service-standalone.jar", "/app/app.jar")
    entryPoint("java")
    defaultCommand("-jar", "/app/app.jar")
    exposePort(8080)
}

tasks.create("buildDockerImage", DockerBuildImage::class) {
    group = "necasond"
    dependsOn("createDockerfile")
    dependsOn("fatJar")
    inputDir.set(file("."))
    dockerFile.set(file("build/docker/Dockerfile"))
    images.add("necasond/uno:latest")
}

tasks {
    register<DockerBuildImage>("dockerBuild") {
        dependsOn("fatJar")

        group = "necasond"

        inputDir.set(file("."))
        dockerFile.set(file("Dockerfile"))
        images.add("necasond/uno:latest")
    }

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
