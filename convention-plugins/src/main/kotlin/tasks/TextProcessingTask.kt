package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*

abstract class TextProcessingTask : DefaultTask() {
    @get:InputFile
    abstract var inputFile: File

    @get:OutputFile
    abstract var outputFile: File
    
    @TaskAction
    fun processText() {
        val text = inputFile.readText()
        val processedText = text.uppercase(Locale.getDefault())
        outputFile.writeText(processedText)
    }
}
