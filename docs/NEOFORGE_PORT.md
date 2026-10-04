# NeoForge 1.21.8 port

Status: **ported and building.** `./gradlew :1.21.8:buildAndCollect` produces
`builds/1.21.8-neoforge/orebushes-2.5+1.21.8.jar`.

## Build setup

- Gradle wrapper **9.1.0**.
- `settings.gradle.kts` — Stonecutter, registers `1.21.8`.
- `stonecutter.gradle.kts` — activates `1.21.8`, ModDevGradle `moddev` 2.0.147.
- `stonecutter.properties.toml` — mod metadata + `deps.neo_loader = "21.8.54"`.
- `build.gradle.kts` — ModDevGradle, Java 21, `buildAndCollect` task.

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
- [ ] Run `:1.21.8:runData` to refresh `src/generated/resources` for 1.21.8 and
      commit the regenerated data.
- [ ] Verify in-game (`:1.21.8:runClient`).

## Reference

- Primers: <https://docs.neoforged.net/primer/docs/1.21.5> and `1.21.6`–`1.21.8`.
- ModDevGradle: <https://docs.neoforged.net/toolchain/docs/plugins/mdg/>
