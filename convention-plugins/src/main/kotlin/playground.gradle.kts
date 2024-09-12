import extensions.GreetExtension
import plugins.HelloPlugin
import plugins.VersioningPlugin
import tasks.FibonacciTask
import tasks.TextProcessingTask

plugins {
    kotlin("jvm")
}

project.extensions.create("greeting", GreetExtension::class)

tasks.register("greet") {
    group = "playground"
    description = "Prints a greeting message"
    doLast {
        val module = project.extensions.getByType(GreetExtension::class).module
        println("Hello, from $module!")
    }
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

tasks.register<Copy>("copyCompiledClasses") {
    group = "build"
    description = "Copy the compiled classes to a specific directory"
    dependsOn("compileKotlin")
    from("app/build/classes/kotlin/main")
    into("compiled-classes")
}

tasks.register<FibonacciTask>("fib_10") {
    group = "playground"
    description = "Calculates the 10th Fibonacci number"
    number = 10
    doFirst {
        println("Calculating the 10th Fibonacci number...")
    }
    doLast {
        println("Calculation complete.")
    }
}

tasks.register<FibonacciTask>("fib_20") {
    group = "playground"
    description = "Calculates the 20th Fibonacci number"
    number = 20
    doFirst {
        println("Calculating the 20th Fibonacci number...")
    }
    doLast {
        println("Calculation complete.")
    }
}

tasks.register<TextProcessingTask>("processText") {
    inputFile = file("input.txt")
    outputFile = file("output.txt")
    doFirst { println("Processing text...") }
    doLast { println("Processing complete.") }
}

apply<HelloPlugin>()
apply<VersioningPlugin>()
