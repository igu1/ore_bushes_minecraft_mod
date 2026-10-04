# NeoForge 26.1.2 port

Status: **ported and building.** `./gradlew :26.1.2:buildAndCollect` produces
`builds/26.1.2-neoforge/orebushes-3.0+26.1.2.jar`.

> Requires **Java 25** (Minecraft 26.1 ships Java 25). The toolchain is resolved
> automatically; set `JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64` if needed.

## Build setup

- Gradle wrapper **9.1.0**.
- `settings.gradle.kts` — Stonecutter, registers `26.1.2`.
- `stonecutter.gradle.kts` — activates `26.1.2`, ModDevGradle `moddev` 2.0.147.
- `stonecutter.properties.toml` — mod metadata + `deps.neo_loader = "26.1.2.108"`.
- `build.gradle.kts` — ModDevGradle, Java 25, `buildAndCollect` task.

## What changed vs 1.21.8 (26.1 is a breaking release)

- **`ResourceLocation` → `Identifier`** (`net.minecraft.resources.Identifier`).
- **`Level` accessors**: `level.isClientSide` → `isClientSide()`, `level.random`
  → `getRandom()`.
- **Item handling capability**: `Capabilities.ItemHandler.BLOCK` + `IItemHandler`
  → `Capabilities.Item.BLOCK` + `ResourceHandler<ItemResource>` (the new
  `net.neoforged.neoforge.transfer` API). `BushHarvesterBlockEntity` now
  implements `ResourceHandler<ItemResource>` and uses
  `Transaction.openRoot()/commit()`.
- **Client rendering extracted**: `GuiGraphics` / `render(...)` →
  `GuiGraphicsExtractor` / `extractRenderState(...)`; container screens
  override `extractBackground(...)` / `extractLabels(...)`; `imageWidth/Height`
  are now `final` and passed to the constructor; `blit` takes a `RenderPipeline`.
- **Particles**: `TextureSheetParticle` → `SingleQuadParticle` with an abstract
  `getLayer()`; `ParticleRenderType` → `SingleQuadParticle.Layer`;
  `ParticleProvider` returns `SingleQuadParticle`; registration stays via
  `RegisterParticleProvidersEvent.registerSpriteSet(...)`.
- **Blocks**: `entityInside(...)` gained an `InsideBlockEffectApplier` and a
  `boolean isPrecise`; `onDestroyedByPlayer(...)` now also takes the `ItemStack`
  tool; `getCloneItemStack(LevelReader, BlockPos, BlockState, boolean)`.
- **Items**: `appendHoverText(ItemStack, TooltipContext, TooltipDisplay,
  Consumer<Component>, TooltipFlag)`.
- **Villager trades**: the `VillagerTradesEvent`/`WandererTradesEvent` hooks were
  removed; trades are now data-driven (`villager_trade` + `trade_set` registries).
- **Worldgen**: the `RANDOM_PATCH` feature was removed; configured/placed
  features are datapack registries declared via `RegistrySetBuilder`.
- **Datagen**: unchanged entry point (`GatherDataEvent`, `includeDev()`).

## Deferred on this branch (build is green without them)

- [ ] **Villager trades** — reimplement as `villager_trade` datapack JSON plus
      `trade_set` overrides for the farmer/wandering-trader sets. The
      `enableVillagerTrades` config currently has no effect (see
      `Events/VillagerTradeHandler`).
- [ ] **Worldgen** — re-declare the configured/placed bush features with a
      `RegistrySetBuilder` (and refresh the biome modifiers, which still use the
      pre-1.21 `forge/biome_modifier` path).
- [ ] **Game tests** — port to the registry-driven test system.
- [ ] Run `:26.1.2:runData` and verify in-game (`:26.1.2:runClient`).

## Reference

- Primer 1.21.11 → 26.1: <https://docs.neoforged.net/primer/docs/26.1>
- ModDevGradle: <https://docs.neoforged.net/toolchain/docs/plugins/mdg/>
