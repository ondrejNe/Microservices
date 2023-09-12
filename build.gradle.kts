import org.gradle.api.plugins.internal.DefaultJavaApplication

plugins {
    val kotlinVersion = libs.versions.kotlin
    kotlin("jvm") version kotlinVersion
}

val javaMajorVersion = libs.versions.java.get().toInt()

kotlin {
    jvmToolchain(javaMajorVersion)

    target {
        compilations.all {
            compilerOptions.configure {
                allWarningsAsErrors.set(false)
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaMajorVersion))
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.register("generateRunConfig") {
    doLast {
        generateRunConfig(rootProject)
    }
}

private val newLine = System.lineSeparator()

fun templateRunConfig(runName: String, moduleName: String, mainClassName: String, folderName: String): String {
    return "<component name=\"ProjectRunConfigurationManager\">$newLine" +
        "  <configuration default=\"false\" name=\"$runName\" type=\"Application\" factoryName=\"Application\" folderName=\"$folderName\">$newLine" +
        "    <option name=\"MAIN_CLASS_NAME\" value=\"$mainClassName\" />$newLine" +
        "    <module name=\"$moduleName.main\" />$newLine" +
        "    <method v=\"2\">$newLine" +
        "      <option name=\"Make\" enabled=\"true\" />$newLine" +
        "    </method>$newLine" +
        "  </configuration>$newLine" +
        "</component>"
}

fun generateRunConfig(rootProject: Project) {
    val runFolder = rootProject.file(".run")
    if (!runFolder.exists()) {
        runFolder.mkdir()
    }

    val rootProjectName = rootProject.name
    rootProject.subprojects
        .forEach {
            val moduleNameEnd = it.name
            var moduleName = it.name
            var currentProject = it
            val firstParentName = currentProject.parent?.name
            while (currentProject.name != rootProjectName) {
                currentProject = currentProject.parent
                moduleName = currentProject.name + "." + moduleName
            }
            val runFileName =
                if ((firstParentName != rootProjectName)) "$firstParentName.$moduleNameEnd"
                else moduleNameEnd
            val runName = runFileName.replace(".", "-")
            val moduleType =
                if ((firstParentName != rootProjectName)) firstParentName
                else "^([^-]+)-".toRegex().find(runName)?.groupValues?.get(1)

            val runContent = templateRunConfig(runName, moduleName, (it.extensions["application"] as DefaultJavaApplication).mainClass.get(), moduleType!!)
            rootProject.file(".run/$runFileName.run.xml").writeText(runContent)
            println("Run configuration generated for $runName")
        }
}
