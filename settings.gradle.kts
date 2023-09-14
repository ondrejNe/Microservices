rootProject.name = "microservices"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

// Project services
include("services:weather.service")
include("services:powerplant.registry.service")
// Project common libs
include("common:redis")
include("common:logging")
include("common:untitled")
findProject(":common:untitled")?.name = "untitled"
