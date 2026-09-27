plugins {
    id("slimeio.common-conventions")
}

dependencies {
    compileOnlyApi(libs.jetbrains.annotations)
    api(libs.jspecify)
    api(libs.zstd)
}
