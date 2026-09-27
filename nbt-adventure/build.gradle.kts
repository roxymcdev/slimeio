plugins {
    id("slimeio.common-conventions")
}

dependencies {
    api(project(":slimeio-core"))
    api(libs.adventure.nbt)
}
