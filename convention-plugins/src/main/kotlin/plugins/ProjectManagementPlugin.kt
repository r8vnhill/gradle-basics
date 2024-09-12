package plugins

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class ProjectManagementPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateReadme") {
            group = "Project Management"
            description = "Generates a README.md file with project information"

            doLast {
                val projectName = project.name
                val projectVersion = project.version.toString()
                val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()

                val readmeContent = """
                    # $projectName
                    
                    Versión: $projectVersion
                    
                    Fecha de creación: $currentDate
                    """.trimIndent()

                val readmeFile = File(project.projectDir, "README.md")
                readmeFile.writeText(readmeContent)
                println("README.md generado en: ${readmeFile.absolutePath}")
            }
        }

        project.tasks.register("cleanLogs") {
            group = "Project Management"
            description = "Cleans all .log files in the logs directory"

            doLast {
                val logsDir = File(project.projectDir, "logs")
                if (logsDir.exists() && logsDir.isDirectory) {
                    val logFiles = logsDir.listFiles { _, name -> name.endsWith(".log") }
                    if (logFiles != null && logFiles.isNotEmpty()) {
                        logFiles.forEach { it.delete() }
                        println("${logFiles.size} archivos de registro eliminados.")
                    } else {
                        println("No se encontraron archivos de registro.")
                    }
                } else {
                    println("La carpeta 'logs' no existe.")
                }
            }
        }
    }
}
