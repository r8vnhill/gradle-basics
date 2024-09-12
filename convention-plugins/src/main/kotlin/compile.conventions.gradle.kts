import extensions.CopyLibExtension

extensions.create<CopyLibExtension>("copyLibConfig")

tasks.register<Copy>("copyLib") {
    group = "build"
    description = "Copies the libraries to the application directory"

    from("build/libs")
    into("$rootDir/app/libs")

    val libNames = project.extensions.getByType<CopyLibExtension>().libNames
    val filesToCopy = mutableListOf<String>() // Lista para almacenar los archivos que deben copiarse

    doFirst { validateLibNames(libNames) }

    // Verificar si el nombre del archivo comienza con algún nombre en la lista de libNames
    include { fileTreeElement ->
        val isJarIncluded = shouldIncludeFile(fileTreeElement.name, libNames, filesToCopy)
        println("Checking file: ${fileTreeElement.name} (Included: $isJarIncluded)") // Depuración
        isJarIncluded
    }

    doLast { verifyCopiedFiles(filesToCopy, "$rootDir/app/libs") }
}

// Función para verificar si el archivo debe incluirse en la lista de archivos a copiar
fun shouldIncludeFile(fileName: String, libNames: List<String>, filesToCopy: MutableList<String>): Boolean {
    val isJarIncluded = libNames.any { fileName.startsWith(it) }
    if (isJarIncluded) {
        filesToCopy.add(fileName)  // Añadir el archivo a la lista de archivos a copiar
    }
    return isJarIncluded
}

// Función para validar que se hayan especificado los nombres de las librerías
fun validateLibNames(libNames: List<String>) {
    if (libNames.isEmpty()) {
        throw GradleException("Error: No library names were specified.")
    }
}

// Función para verificar que todos los archivos hayan sido copiados correctamente
fun verifyCopiedFiles(filesToCopy: List<String>, targetDirPath: String) {
    val targetDir = file(targetDirPath)

    if (isValidDirectory(targetDir)) {
        println("Libraries copied successfully to app/libs")

        val copiedFiles = getCopiedFileNames(targetDir)
        val missingFiles = findMissingFiles(filesToCopy, copiedFiles)

        printMissingFilesReport(missingFiles)

        if (missingFiles.isNotEmpty()) {
            throw GradleException("Error: Some libraries were not copied.")
        }
    } else {
        throw GradleException("Error: Target directory does not exist or the copy operation failed.")
    }
}

// Función para verificar si el directorio de destino es válido
fun isValidDirectory(directory: File) = directory.exists() && directory.isDirectory

// Función para obtener los nombres de los archivos copiados
fun getCopiedFileNames(directory: File) = directory.listFiles()?.map { it.name } ?: emptyList()

// Función para encontrar los archivos faltantes que no se copiaron
fun findMissingFiles(filesToCopy: List<String>, copiedFiles: List<String>) =
    filesToCopy.filterNot { it in copiedFiles }

// Función para imprimir el reporte de archivos faltantes
fun printMissingFilesReport(missingFiles: List<String>) = if (missingFiles.isEmpty()) {
    println("All expected libraries were copied successfully.")
} else {
    println("Warning: The following files were not copied: $missingFiles")
}

tasks.named("copyLib") {
    dependsOn("jar")
}
