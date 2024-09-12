package plugins

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.gradle.api.Plugin
import org.gradle.api.Project

class VersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Definir una tarea que genera un archivo version.txt
        project.tasks.register("generateVersionFile") {
            group = "Versioning"
            description = "Generates a version file with the project version and build date"

            doLast {
                val version = project.version.toString()
                val currentMoment = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val dateTime = currentMoment.toString().split('.')[0].replace('T', ' ')

                val versionFile = project.layout.buildDirectory.file("version.txt").get().asFile

                if (!versionFile.exists()) {
                    versionFile.createNewFile()
                }

                versionFile.appendText("Version: $version\nBuild Date: $dateTime\n\n")

                println("Version file updated at: ${versionFile.absolutePath}")
            }
        }
    }
}
