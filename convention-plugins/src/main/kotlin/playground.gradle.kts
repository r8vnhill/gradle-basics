plugins {
    kotlin("jvm")
}

tasks.register("message") {
    doFirst {
        println("Calculating the Fibonacci sequence...")
    }
}

tasks.register("fib") {
    group = "Playground"
    description = "Calculates the Fibonacci sequence up to the 10th number"
    dependsOn("message")

    var first = 0
    var second = 1

    doFirst {
        for (i in 1..10) {
            print("$first ")
            second += first
            first = second - first
        }
    }

    doLast {
        println("\nThe 10th Fibonacci number is: $first")
    }
}

tasks.register("greet") {
    group = "Playground"
    description = "Prints a greeting message"
    println("Hello, Gradle!")
}

tasks.register("countCompiledSize") {
    group = "build"
    description = "Count the size of the compiled classes"
    dependsOn("compileKotlin")
    doLast {
        val files = fileTree("app/build/classes/kotlin/main").files +
                fileTree("lib/build/classes/kotlin/main").files
        var size = 0L
        for (file in files) {
            size += file.length()
        }
        println("The size of the compiled classes in subproject1 is $size bytes")
    }
}
