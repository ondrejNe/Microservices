rootProject.name = "microservices"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }

    repositories {
        mavenCentral()
    }
}

include("uno.service")
include("dos.service")
