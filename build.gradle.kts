plugins {
    id("jvm.conventions")
    id("playground")
    alias(libs.plugins.detekt)
}

val projectGroup = extra["echo.group"]!! // Arroja una excepción si no se encuentra la propiedad
val projectVersion = libs.versions.echo.get()
val detektId = libs.plugins.detekt.get().pluginId
val detektFormattingModule = libs.detekt.formatting.get().module.toString()
val detektFormattingVersion = libs.detekt.formatting.get().version

allprojects {
    group = projectGroup
    version = projectVersion
}

subprojects {
    apply(plugin = "jvm.conventions")
    apply(plugin = detektId)

    dependencies {
        detektPlugins("$detektFormattingModule:$detektFormattingVersion")
    }
}

greeting {
    module = project.name
}
