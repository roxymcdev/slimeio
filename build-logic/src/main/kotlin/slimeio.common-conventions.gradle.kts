plugins {
    id("net.kyori.indra")
    id("net.kyori.indra.publishing")
}

repositories {
    mavenCentral()
}

indra {
    javaVersions {
        target(17)
    }

    publishReleasesTo("roxymc", "https://repo.roxymc.net/releases")
    publishSnapshotsTo("roxymc", "https://repo.roxymc.net/snapshots")
}

tasks.withType<Sign>().configureEach {
    enabled = false
}
