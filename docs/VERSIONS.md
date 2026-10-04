# Version branches & local builds

This repository keeps **one branch per major Minecraft/loader target**. Each branch is
self-contained: it has its own build script and produces its own jar. A shared,
git-ignored `builds/` folder at the repository root collects every version's jar so
they can be copied out for release without polluting git history.

## Branch layout

| Branch | Target | Build system | Java |
|---|---|---|---|
| `Main` | Forge 1.19 (current development) | ForgeGradle 5.1 | 17 |
| `version/1.19-forge` | Forge 1.19 (release snapshot) | ForgeGradle 5.1 | 17 |
| `version/1.20.1-neoforge` | NeoForge 1.20.1 | Stonecutter + ModDevGradle (legacy) | 17 |
| `version/1.21.1-neoforge` | NeoForge 1.21.1 | Stonecutter + ModDevGradle | 21 |

The NeoForge branches use [Stonecutter](https://stonecutter.kikugie.dev/) so that
additional NeoForge point releases can be added to a single branch by listing them
in `settings.gradle.kts` and `stonecutter.properties.toml`. Code differences between
those releases are expressed with Stonecutter condition comments in `src/`.

> **Status:** the NeoForge branches are scaffolding only. Their `src/` tree is still
> the Forge 1.19 code and has **not** been ported yet. See `docs/NEOFORGE_PORT.md`
> on those branches for the checklist.

## `builds/`

`builds/` is listed in `.gitignore`, so its contents are **never committed**. Switch
branches and run the collect task to accumulate one jar per version:

| Folder | Produced by |
|---|---|
| `builds/1.19-forge/` | `./gradlew collectBuild` on `Main` / `version/1.19-forge` |
| `builds/1.20.1-neoforge/` | `./gradlew buildAndCollect` on `version/1.20.1-neoforge` |
| `builds/1.21.1-neoforge/` | `./gradlew buildAndCollect` on `version/1.21.1-neoforge` |

Only **major** targets get a branch and a jar: `1.19`, `1.20.1`, `1.21.1`. Sub/point
releases are handled inside the NeoForge branches via Stonecutter rather than as
separate branches.

## Adding a version

1. **NeoForge:** add the version to `versions(...)` in `settings.gradle.kts` and a
   `["<version>"]` block in `stonecutter.properties.toml` on the matching branch.
   Add condition comments in `src/` where the APIs differ.
2. **Other loader / incompatible API:** create a new `version/<target>` branch and
   port the `src/` tree, or fork the closest NeoForge/Forge branch.
