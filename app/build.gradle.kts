plugins {
    application
}

dependencies {
    implementation(
        fileTree("libs") {
            include("lib-1.0.0.jar")
            // include("...")
        }
    )
}

application {
    mainClass.set("cl.ravenhill.EchoAppKt")
}

tasks.named("compileKotlin") {
    dependsOn(":lib:copyLib")
}
