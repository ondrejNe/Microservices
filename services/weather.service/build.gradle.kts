//import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
//import com.bmuschko.gradle.docker.tasks.image.Dockerfile

plugins {
    val kotlinVersion = libs.versions.kotlin
    val kotlinSpringVersion = libs.versions.kotlinSpring

    java
    kotlin("jvm") version kotlinVersion
    application

    kotlin("plugin.spring") version kotlinSpringVersion
    id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"

//    id("com.bmuschko.docker-remote-api") version libs.versions.dockerRemoteApi
//    id("com.bmuschko.docker-spring-boot-application") version "9.3.2"
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
    mainClass.set("org.necasond.Main")
}

//docker {
//    url = "unix:///var/run/docker.sock"
//    springBootApplication {
//        baseImage.set("openjdk:8-alpine")
//        ports.set(listOf(9090, 8080))
//        images.set(setOf("awesome-spring-boot:1.115", "awesome-spring-boot:latest"))
//        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
//    }
//}

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

//    implementation("com.bmuschko:gradle-docker-plugin:9.3.2")

}

tasks {
//    register<Dockerfile>("createDockerfile") {
//        group = "necasond"
//        destFile.set(file("build/docker/Dockerfile"))
//        from("openjdk:17-jdk")
//        copyFile("/libs/weather.service-standalone.jar", "/app/app.jar")
//        entryPoint("java")
//        defaultCommand("-jar", "/app/app.jar")
//        exposePort(8080)
//        runCommand("apk --update --no-cache add curl")
//        instruction("HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1")
//    }

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
