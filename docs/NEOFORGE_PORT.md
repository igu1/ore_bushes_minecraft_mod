# NeoForge 1.21.1 port

Status: **ported and building.** `./gradlew :1.21.1:buildAndCollect` produces
`builds/1.21.1-neoforge/orebushes-2.5+1.21.1.jar`.

## Build setup

- Gradle wrapper **9.1.0** (Stonecutter 0.9.8 requires Gradle 9).
- `settings.gradle.kts` — Stonecutter, registers `1.21.1`.
- `stonecutter.gradle.kts` — activates `1.21.1`, ModDevGradle `moddev` 2.0.147.
- `stonecutter.properties.toml` — mod metadata + `deps.neo_loader = "21.1.251"`.
- `build.gradle.kts` — ModDevGradle, Java 21, `buildAndCollect` task.
- Mod metadata moved to `META-INF/neoforge.mods.toml` with `type="required"`.

## What changed vs. 1.20.1

1.20.5+ renamed the whole NeoForge package tree from `net.minecraftforge.*` to
`net.neoforged.neoforge.*` and made further API changes:

- **Imports/mods**: `DeferredHolder`, `ModConfigSpec`, `IMenuTypeExtension`,
  `net.neoforged.bus.api`, `EventBusSubscriber`, `ModContainer`, `NeoForge.EVENT_BUS`.
- **Blocks**: `codec()` is now required on every `Block`/`BaseEntityBlock`
  (`MapCodec`, `RecordCodecBuilder`/`simpleCodec`); `use` split into
  `useWithoutItem`/`useItemOn` returning `ItemInteractionResult`.
- **Item tooltips**: `appendHoverText(ItemStack, Item.TooltipContext, ...)`.
- **Bonemeal**: `isValidBonemealTarget(LevelReader, BlockPos, BlockState)` (3 args).
- **Clone**: `getCloneItemStack(LevelReader, BlockPos, BlockState)`.
- **Persistence**: `load` → `loadAdditional(CompoundTag, HolderLookup.Provider)`.
- **Capabilities**: `getCapability`/`LazyOptional` → `RegisterCapabilitiesEvent`
  + `Capabilities.ItemHandler.BLOCK` + `level.getCapability(...)`.
- **Networking/menus**: `NetworkHooks.openScreen` → `ServerPlayer.openMenu`;
  screens via `RegisterMenuScreensEvent`; `renderBackground(GuiGraphics,...)`.
- **Enchantments**: data-driven; `Enchantments.FORTUNE` is a `ResourceKey` and
  the level registry is used to resolve the `Holder` for Fortune.
- **Crop growth**: `ForgeHooks/CommonHooks.onCropsGrowPost` removed → guard with
  `CommonHooks.canCropGrow`.
- **DataGen**: `RecipeProvider` now takes `(PackOutput, CompletableFuture<Provider>)`
  and emits `RecipeOutput`; `BlockLootSubProvider` needs a `HolderLookup.Provider`;
  loot providers come from `SubProviderEntry`.
- **Misc**: `ResourceLocation` factory methods; `Properties.ofFullCopy`;
  `ItemCost`/`Optional<ItemCost>` in `MerchantOffer`.

## Not yet done

- [ ] Game tests moved to `docs/port-backlog/ResourcePlantGameTests.java.1.21.1`
      — re-add with the NeoForge 1.21 gametest API.
- [ ] Run `:1.21.1:runData` to refresh `src/generated/resources` for 1.21 and
      commit the regenerated data.
- [ ] Verify in-game (`:1.21.1:runClient`).

## Reference

- ModDevGradle: <https://docs.neoforged.net/toolchain/docs/plugins/mdg/>
- Stonecutter: <https://stonecutter.kikugie.dev/>
