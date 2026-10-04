plugins {
    id("net.neoforged.moddev")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

// Minecraft 1.21.x runs on Java 21.
java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    withSourcesJar()
}

neoForge {
    version = property("deps.neo_loader") as String
    validateAccessTransformers = true

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        register("client") {
            gameDirectory = rootProject.file("run")
            client()
        }
        register("server") {
            gameDirectory = rootProject.file("run")
            server()
        }
        register("data") {
            gameDirectory = rootProject.file("run")
            data()
        }
    }
}

tasks {
    // Stonecutter must process the sources before Minecraft artifacts are built.
    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the NeoForge 1.21.1 jar and copies it into builds/1.21.1-neoforge/"
        from(layout.buildDirectory.dir("libs")) {
            include("*.jar")
            exclude("*-sources.jar", "*-dev.jar")
        }
        into(rootProject.file("builds/1.21.1-neoforge"))
        dependsOn("build")
    }
}
