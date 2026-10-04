# NeoForge 1.21.8 port

Status: **ported, building and verified.** `./gradlew :1.21.8:buildAndCollect`
produces `builds/1.21.8-neoforge/orebushes-2.0.0+1.21.8.jar`.

Verified end to end: `:1.21.8:runClient` reaches the main menu with zero missing
block/item models and no `@OnlyIn` warning, and `:1.21.8:runServer` reaches
`Done` with the datapack worldgen and biome modifiers loaded.

## Build setup

- Gradle wrapper **9.1.0**.
- `settings.gradle.kts` — Stonecutter, registers `1.21.8`.
- `stonecutter.gradle.kts` — activates `1.21.8`, ModDevGradle `moddev` 2.0.147.
- `stonecutter.properties.toml` — mod metadata + `deps.neo_loader = "21.8.54"`.
- `build.gradle.kts` — ModDevGradle, Java 21, `buildAndCollect` task.

## Resource / worldgen parity pass

Earlier the branch compiled but shipped the 1.21.1-era resources, so it loaded
with broken item models and no natural generation. Fixed to match the 26.1.2
branch:

- **Item model definitions** — added `assets/orebushes/items/*.json` (24), required
  since 1.21.4. Previously every item logged
  `No model loaded for default item model ID orebushes:<item>`.
- **Worldgen datagen** — configured/placed features are datapack registries since
  1.21, so the old runtime `DeferredRegister` worldgen was replaced with
  `BootstrapContext` bootstrap + `GatherDataEvent#createDatapackRegistryObjects`
  (`Datagen/DataGen.java`, `WorldGen.java`). Output is committed under
  `src/generated/resources/data/orebushes/worldgen/`.
- **Biome modifiers** — moved `data/orebushes/forge/biome_modifier/` to
  `data/orebushes/neoforge/biome_modifier/` and switched the type from
  `forge:add_features` to `neoforge:add_features` (the `forge` namespace is gone
  in 1.21.x).
- **Loot table path** — moved the hand-authored harvester table from
  `loot_tables/` to the 1.21 `loot_table/` folder so it actually drops.
- **`@OnlyIn` removed** from `Client/BushHarvesterScreen` (member stripping was
  removed, so NeoForge logged it as an error).
- **Version manifest** — `processResources`/`jar` now write `Implementation-Version`
  so the Mod List shows the real version instead of `0.0NONE`.


## What changed vs 1.21.1 (spans 1.21.2 → 1.21.8)

- **Render pipeline rework (1.21.5)**: `GuiGraphics.blit` now takes a
  `RenderPipeline` (`RenderPipelines.GUI_TEXTURED`); `RenderSystem` shader calls
  removed.
- **Event bus**: `@EventBusSubscriber` no longer has a `bus`/`Bus` — everything is
  on the mod bus.
- **`InteractionResult`**: `sidedSuccess(boolean)` removed → `SUCCESS` /
  `SUCCESS_SERVER`.
- **Block removal (1.21.5)**: `onRemove` → `affectNeighborsAfterRemoval`; container
  drops moved to `BlockEntity#preRemoveSideEffects`.
- **`BlockEntity` persistence**: `loadAdditional`/`saveAdditional` now take
  `ValueInput`/`ValueOutput` instead of `CompoundTag, HolderLookup.Provider`.
- **`ContainerHelper`**: `loadAllItems(ValueInput, list)` / `saveAllItems(ValueOutput, list)`.
- **`entityInside`**: now receives `InsideBlockEffectApplier`.
- **`getCloneItemStack`**: NeoForge player-sensitive
  `(LevelReader, BlockPos, BlockState, boolean, Player)`.
- **Items**: `appendHoverText(ItemStack, TooltipContext, TooltipDisplay, Consumer<Component>, TooltipFlag)`.
- **`BlockEntityType`**: `Builder` removed → `new BlockEntityType<>(supplier, blocks...)`.
- **Registry access**: `registryOrThrow` → `lookupOrThrow`.
- **`Direction#getNormal`** → `getUnitVec3i`.
- **DataGen**: NeoForge `BlockStateProvider`/`ItemModelProvider`/`ExistingFileHelper`
  removed; `GatherDataEvent` unified (no `includeClient`/`includeServer`, use
  `includeDev()`); `RecipeProvider` now `(HolderLookup.Provider, RecipeOutput)` with
  a `Runner`, no-arg `buildRecipes()`, builders take a `HolderGetter<Item>`.
- **Data-driven enchantments**: Fortune resolved via `lookupOrThrow(...).getOrThrow(...)`.

The hand-authored plant models under `assets/orebushes/models/block/plants/` are the
source of truth, so the model data providers were removed rather than ported.

## Not yet done

- [ ] Game tests — the 1.21.5 overhaul replaced the annotation system with a
      registry/data-driven one. Re-add a test instance separately.

## Reference

- Primers: <https://docs.neoforged.net/primer/docs/1.21.5> and `1.21.6`–`1.21.8`.
- ModDevGradle: <https://docs.neoforged.net/toolchain/docs/plugins/mdg/>
