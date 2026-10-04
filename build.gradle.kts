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
        register("clientData") {
            gameDirectory = rootProject.file("run")
            clientData()
            programArguments.addAll("--mod", property("mod.id") as String, "--all",
                    "--output", rootProject.file("src/generated/resources").absolutePath,
                    "--existing", rootProject.file("src/main/resources").absolutePath)
        }
    }
}

// Datagen output (translations, recipes, loot tables) must ship in the jar.
// Static assets (blockstates, models, item definitions) live in src/main/resources.
sourceSets.main {
    resources.srcDir("src/generated/resources")
}

tasks {
    // Stonecutter must process the sources before Minecraft artifacts are built.
    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the NeoForge 1.21.8 jar and copies it into builds/1.21.8-neoforge/"
        from(layout.buildDirectory.dir("libs")) {
            include("*.jar")
            exclude("*-sources.jar", "*-dev.jar")
        }
        into(rootProject.file("builds/1.21.8-neoforge"))
        dependsOn("build")
    }
}
