# NeoForge 1.21.1 port checklist

This branch is **scaffolding**. The build system has been switched to
Stonecutter + ModDevGradle, but `src/` is still the Forge 1.19 code and does not
compile against NeoForge 1.21.1 yet. Work through the checklist, then run
`./gradlew buildAndCollect` to drop the jar into `builds/1.21.1-neoforge/`.

## Build setup (done)

- `settings.gradle.kts` — Stonecutter 0.9.8, registers `1.21.1`.
- `stonecutter.gradle.kts` — activates `1.21.1`, ModDevGradle `moddev` 2.0.147.
- `stonecutter.properties.toml` — mod metadata + NeoForge loader version.
- `build.gradle.kts` — ModDevGradle config, Java 21, `buildAndCollect` task.
- Gradle wrapper bumped to 8.12.

## Still to do

- [ ] Replace `src/main/resources/META-INF/mods.toml` with the modern
      `src/main/resources/META-INF/neoforge.mods.toml` (renamed in 1.20.5):
  - `loaderVersion="[1,)"`, `modId="orebushes"`, correct display name/credits.
  - `[[dependencies.orebushes]]` for `neoforge`/`minecraft` with the `[1.21, 1.21.1]`
    range.
- [ ] Port the `me.ez.orebushes` sources from Forge 1.19 to NeoForge 1.21.1
      (event bus split, `DeferredRegister`/`DeferredHolder`, registry/data-driven
      changes, `ResourceLocation` factories, no more `net.minecraftforge.*`).
- [ ] Move mod metadata fields the build expands (`${id}`, `${name}`, `${version}`,
      `${minecraft}`) into the toml, or drop the expansion.
- [ ] Re-verify assets/data (`src/main/resources`, `src/generated/resources`);
      datapack folder names are depluralised in 1.21.
- [ ] Confirm `annotationProcessor` mixin refmap config if mixins are added.

## Reference

- Stonecutter: <https://stonecutter.kikugie.dev/>
- ModDevGradle: <https://docs.neoforged.net/toolchain/docs/plugins/mdg/>
