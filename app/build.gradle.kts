plugins {
    id("jvm.conventions")
    application
    alias(libs.plugins.detekt)
}

dependencies {
    implementation(libs.kotlinx.datetime)
}

application {
    mainClass.set("cl.ravenhill.EchoKt")
}
