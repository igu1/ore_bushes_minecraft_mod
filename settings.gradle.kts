pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    // Multi-version source preprocessing / project management.
    // See https://stonecutter.kikugie.dev
    id("dev.kikugie.stonecutter") version "0.9.8"
    // Lets Gradle download the required JDK for the toolchain if it is missing.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        // Major target for this branch. Add further NeoForge point releases here
        // (and a matching block in stonecutter.properties.toml) to build them from
        // this single codebase with Stonecutter condition comments.
        versions("1.21.8")
        vcsVersion = "1.21.8"
    }
}

rootProject.name = "orebushes"
