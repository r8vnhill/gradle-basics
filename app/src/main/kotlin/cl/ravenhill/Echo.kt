package cl.ravenhill

import kotlinx.datetime.*

fun echo(message: String) = "${Clock.System.now()} - $message"

fun main(args: Array<String>){
    for (arg in args) {
        println(echo("Hola, mundo!"))
    }
}
