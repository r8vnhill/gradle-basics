package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

/**
 * A Gradle task to calculate the n-th Fibonacci number.
 *
 * ## Usage:
 * This task calculates the Fibonacci number for the specified input `number`. Ensure that the input number is a
 * non-negative integer, as negative values will result in a task failure.
 *
 * ### Example 1: Calculate Fibonacci for 5
 * ```kotlin
 * tasks.register("fibonacciTask", FibonacciTask::class) {
 *     number.set(5)
 * }
 * ```
 * The output will be: "The 5-th Fibonacci number is: 5"
 *
 * ### Example 2: Calculate Fibonacci for 10
 * ```kotlin
 * tasks.register("fibonacciTask", FibonacciTask::class) {
 *     number.set(10)
 * }
 * ```
 * The output will be: "The 10-th Fibonacci number is: 55"
 *
 * @property number The number for which to calculate the Fibonacci sequence. Must be greater than or equal to 0.
 *
 * @throws StopExecutionException if the provided `number` is less than 0.
 */
abstract class FibonacciTask : DefaultTask() {
    @get:Input
    abstract val number: Property<Int>

    @TaskAction
    fun calculateFibonacci() {
        val n = number.get()
        if (n < 0) {
            throw StopExecutionException("The number must be greater than or equal to 0")
        }
        var first = 0
        var second = 1
        repeat(n) {
            print("$first ")
            second += first
            first = second - first
        }
        println("The $n-th Fibonacci number is: $first")
    }
}
