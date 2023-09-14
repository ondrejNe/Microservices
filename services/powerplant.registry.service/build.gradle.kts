plugins {
    // JVM
    java
    application
    kotlin("jvm") version libs.versions.kotlin
    // Spring
    kotlin("plugin.spring") version libs.versions.kotlinSpring
    alias(libs.plugins.springBootPlugin)
    alias(libs.plugins.springDependencyManagementPlugin)
    // Open API
    alias(libs.plugins.openapiGenerator)
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
// Open API specifications
openApiGenerate {
    generatorName.set("kotlin-spring")

    configOptions.set(
        mapOf(
            "delegatePattern" to "true",
            "dateLibrary" to "java8",
            "useTags" to "true",
        )
    )
    apiPackage.set("${project.name}.api")
    invokerPackage.set("${project.name}.invoker")
    modelPackage.set("${project.name}.model")

    inputSpec.set("${projectDir}/openapi.yaml")
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("${projectDir}/build/generate-resources/main/src/main/kotlin"))
        }
    }
    test {
        java {
            setSrcDirs(
                listOf(
                    "${projectDir}/src/test/integration",
                    "${projectDir}/src/test/unit"
                )
            )
        }
    }
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
    implementation(libs.springBootKotlinStdLib)
    // Kotlin
    implementation(libs.kotlinCoroutines)
    implementation(libs.kotlinCoroutinesJdk8)
    implementation(libs.kotlinCoroutinesMdc)
    implementation(libs.kotlinReflections)
    // Logging
    implementation(project(":common:logging"))
    // Open API
    implementation(libs.springDocOpenApi)
    implementation(libs.swaggerModels)
    implementation(libs.swaggerAnnotations)
    implementation(libs.javaxValidationApi)
    implementation(libs.javaxServletApi)
    // Redis
    implementation(libs.kreds)
    implementation(project(":common:redis"))

    // Testing
    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.junitJupiterParams)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoInline)
    testImplementation(libs.mockitoKotlin)
    testImplementation(libs.mockitoJunitJupiter)
    testImplementation(libs.assertJ)
    testImplementation(libs.kotlinCoroutineTest)
}

tasks {
    register("sourceSetInfo") {
        group = "necasond"

        doLast {
            sourceSets.forEach { srcSet ->
                println("[${srcSet.name}]")
                print("-->Source directories: "+srcSet.allJava.srcDirs+"\n")
                print("-->Output directories: "+srcSet.output.classesDirs.files+"\n")
                print("-->Compile classpath:\n")
                srcSet.compileClasspath.files.forEach {
                    print("  "+it.path+"\n")
                }
                println("")
            }
        }
    }

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
