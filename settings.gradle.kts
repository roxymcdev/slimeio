pluginManagement {
    includeBuild("build-logic") {
        name = "slimeio-$name"
    }
}

rootProject.name = "slimeio-parent"

fun include(path: String, action: ProjectDescriptor.() -> Unit) {
    include(path)
    project(path).action()
}

listOf(
    "core",
    "nbt-adventure"
).forEach { module ->
    include(":slimeio-$module") {
        projectDir = file(module)
    }
}
