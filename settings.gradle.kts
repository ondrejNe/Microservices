rootProject.name = "microservices"

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

include("uno.service")
include("dos.service")
