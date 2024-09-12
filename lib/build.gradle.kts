plugins {
    id("compile.conventions")
}

dependencies {
    implementation(libs.kotlinx.datetime)
}

copyLibConfig {
    libNames = listOf(project.name)
}
