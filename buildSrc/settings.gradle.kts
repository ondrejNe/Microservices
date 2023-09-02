rootProject.name = "necasond-conventions"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
    }
}
