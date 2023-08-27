rootProject.name = "microservices"

include("uno.service")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
