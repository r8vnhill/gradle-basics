package cl.ravenhill

import kotlinx.datetime.Clock

fun echo(message: String) = "${Clock.System.now()} - $message"
