rootProject.name = "gradle-basics"

include(
    ":app", // Include a submodule for the application
    ":lib"  // Include a submodule for the library
)

pluginManagement { // Configuración de gestión de plugins
    includeBuild("convention-plugins") // Incluye un archivo de configuración de plugins
    repositories {  // Repositorios de plugins
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {    // Gestión de resolución de dependencias
    @Suppress("UnstableApiUsage")   // Necesario para usar la API de resolución de dependencias
    repositories {
        mavenCentral()  // Repositorio Maven Central
    }
}
