package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class NumbersTask : DefaultTask() {
    @get:InputFile
    abstract var inputFile: File

    @get:OutputFile
    abstract var outputFile: File

    @TaskAction
    fun processNumbers() {
        val lines = inputFile.readLines()
        val results = mutableListOf<String>()  // Lista para almacenar los resultados
        for (line in lines) {
            try {
                val num = line.toInt()  // Convertir el valor leído a entero
                val result = if (num % 2 == 0) "$num: par" else "$num: impar"
                results += result
            } catch (e: NumberFormatException) {
                results += "$line: no es un número válido"
            }
        }
        outputFile.writeText(results.joinToString("\n"))
    }
}