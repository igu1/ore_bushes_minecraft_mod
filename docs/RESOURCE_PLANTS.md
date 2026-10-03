<p align="center">
  <img src="../art/branding/ore-bushes-banner.png" alt="Ore Bushes" width="100%">
</p>

# Resource plants — Forge 1.19 redesign

Every plant has its own Blockbench-built silhouette, three pixel-art materials,
four growth stages (rooted, juvenile, budding, ripe), a spent model, a resource-colored
particle sprite and an original short mono Vorbis harvest sound with subtitles.

The active models are the **v2 resource-growth** set: foliage-free, resource-themed
shapes that fill a full 16×16 footprint, with four flat materials and only small
highlights. Assets use `<plant>_v2_<stage>.json` and `<plant>_v2_<role>.png`
(`art/previews/resource-growths-v2`). The Ore Harvester uses the matching
`harvester_v2_*` textures and `<mode>_v2` models. Install or refresh everything
with `python3 art/install_v2.py` and verify with `python3 art/validate_assets.py`.

Selection outlines are deliberately simple: a shallow footprint box and one
broad crown box per resource. `ResourcePlantShapes` caches ages 0–3 at the same
0.30 / 0.52 / 0.78 / 1.00 scale as the models, plus a 0.65-scale spent outline.
Spent outlines override age; ordinary harvest counts do not change the outline.
Bushes remain walk-through. These are not detailed per-cube collision meshes.
Starter items use the juvenile 3D model instead of the old flat seed sprite.

## Survival rules

- Right-click **ripe (age 3)** plants to harvest. Age 2 no longer produces resources.
- Harvesting keeps the plant ripe and permanently increments its `harvests`
  blockstate. The count survives saves/reloads. There is no fertilizer reset and
  no regrowth wait between harvests.
- Lifetime harvests are configurable per tier (`harvestsTier1`..`harvestsTier4`,
  defaults 15 / 10 / 6 / 4).
- At its lifetime limit the plant becomes spent: no growth, drops or particles.
- Breaking always returns one starter seed, whatever the age or remaining harvest
  count, so plants can be relocated freely. A ripe, non-spent plant additionally
  drops one resource on destruction. Fortune/Silk Touch still cannot increase
  breaking loot.
- Plants can only be planted on their listed substrate and in their listed
  dimension; the placement check matches the growth conditions.
- Hand and machine harvests share the same yield function and lifetime transition.
  A full inventory above a harvester does not consume a harvest.
- Tier 1/2 harvests give 2 resources by default, configurable but capped at 3.
  Fortune can add at most one, still capped at 3. Tier 3/4 always yield exactly 1;
  Fortune, mature bonuses and amount multipliers cannot inflate rare resources.
- Metal/gem resources still use nuggets where listed below; 9 nuggets make a unit.
- Growth requires the listed dimension, substrate and depth. With the default
  global growth setting, one stage advances with probability `1 / (4 × tier)`
  per random tick. This is not a fixed real-time cooldown. Light requirements
  remain optionally configurable.
- Bone meal only works on tier 1, and only under valid growing conditions.
- Wild plants start ripe so rare discoveries can be harvested immediately, and
  they stay ripe until spent. The listed growth conditions only matter for
  planted starters that still have to mature. Plants can be relocated freely by
  breaking, which returns the starter seed.

## Plant collection

| Resource / harvest | New name | Tier | Harvests | Growing conditions |
|---|---|---:|---:|---|
| Coal | Cinder Fern | 1 | 15 | Overworld, grass/farmland |
| Iron nugget | Ferric Reed | 2 | 10 | Overworld, farmland |
| Gold nugget | Gilded Lotus | 2 | 10 | Overworld, farmland |
| Emerald nugget | Verdant Spire | 3 | 6 | Overworld, moss |
| Redstone | Pulse Vine | 2 | 10 | Overworld, farmland |
| Lapis lazuli | Azure Fan | 2 | 10 | Overworld, farmland |
| Diamond nugget | Prismatic Crown | 3 | 6 | Overworld, deepslate, Y < 0 |
| Copper nugget | Patina Frond | 1 | 15 | Overworld, grass/farmland |
| Amethyst shard | Chiming Cluster | 2 | 10 | Overworld, amethyst block |
| Experience bottle | Insight Cap | 3 | 6 | Overworld, moss |
| Echo shard | Echo Tendril | 4 | 4 | Overworld, sculk, Y < 0 |
| Golden apple | Aureate Bonsai | 4 | 4 | Overworld, moss |
| Sugar | Frosted Cane | 1 | 15 | Overworld, grass/farmland |
| Quartz | Ivory Thorn | 2 | 10 | Nether, netherrack |
| Glowstone dust | Lantern Bloom | 2 | 10 | Nether, soul soil |
| Netherite nugget | Obsidian Heart | 4 | 4 | Nether, basalt, Y < 32 |
| Ancient debris | Relic Knuckle | 4 | 4 | Nether, basalt, Y < 32 |
| Blaze rod | Ember Antler | 3 | 6 | Nether, magma block |
| Ender pearl | Rift Pod | 3 | 6 | End, end stone |
| Ender eye | Watcher Orchid | 4 | 4 | End, end stone |
| Chorus fruit | Chorus Candelabra | 2 | 10 | End, end stone |
| Shulker shell | Shell Rosette | 4 | 4 | End, purpur block |
| Dragon breath | Dragon Chalice | 4 | 4 | End, obsidian |

Pulse Vine, Chiming Cluster and Insight Cap emit light level 4 when ripe;
Lantern Bloom and Ember Antler emit 8; Dragon Chalice emits 6. Hand-harvesting
Insight Cap grants 10 seconds of night vision; Aureate Bonsai grants 3 seconds
of regeneration. These small effects do not bypass resource limits.

## Obtaining starters

Recipes produce **one** starter:

```text
 C
SBS
 C
```

`S` is wheat seeds for Overworld plants, nether wart for Nether plants, and
chorus flowers for End plants (two per recipe). `C` is a progression catalyst:
bone meal at tier 1, amethyst shards at tier 2, ender pearls at tier 3, and echo
shards at tier 4 (two per recipe). Echo Tendril instead uses sculk sensors.

`B` is one resource-specific core:

| Plant | Core |
|---|---|
| Cinder Fern | Charcoal |
| Ferric Reed / Gilded Lotus | Iron / gold nugget |
| Verdant Spire / Prismatic Crown / Patina Frond / Obsidian Heart | Corresponding mod nugget |
| Pulse Vine / Azure Fan / Chiming Cluster | Redstone / lapis / amethyst shard |
| Insight Cap | Glass bottle |
| Echo Tendril | Sculk catalyst |
| Aureate Bonsai / Frosted Cane | Apple / sugar cane |
| Ivory Thorn / Lantern Bloom | Quartz / glowstone dust |
| Relic Knuckle / Ember Antler | Netherite scrap / blaze powder |
| Rift Pod / Watcher Orchid / Chorus Candelabra | Popped chorus fruit / ender eye / chorus fruit |
| Shell Rosette / Dragon Chalice | Purpur block / dragon breath |

Rare resources therefore need exploration/progression rather than ordinary seeds
alone. Existing trader listings now sell one starter per offer, require the
appropriate tier catalyst as a second payment, cost more emeralds by tier, and
have reduced stock. Trades do not bypass growing conditions or lifetime limits.

## Ore Harvester

The new Blockbench machine is exactly **16 × 16 × 16 model units** with nine
16 × 16 pixel textures and 62–63 modeled parts: steel casing, bronze supports,
corner rivets, side vents, safety grilles, a visible cutter/spindle, control panel,
status lamp, four cutout-glass windows, output aperture and state-specific shutter/brake details.
All window bars connect to rails, rivets sit against the casing, and cutters meet
the hub without intersecting. The asset validator checks rotated parts for overlaps.

| State | Indicator | Behavior |
|---|---|---|
| Idle | Blue | Waiting for its harvest interval or no ripe plants |
| Running | Green | Successful harvest; cutter pose, sparks, radius effect and light for 2 seconds |
| Blocked | Red | Internal storage and any inventory above are full; shutter closes |
| Paused | Amber | An adjacent redstone signal pauses the machine and resets its timer |

Right-click opens a **text-free, ore-harvester-themed screen** backed by a 176×184
hand-built GUI texture (steel panel, bronze trim, gear/ore emblem, status lamps).
It provides **36 internal storage slots** (4 rows of 9) plus the player inventory;
shift-click moves stacks between them. The machine fills its own storage first and
only pushes overflow into an item handler (for example a chest) directly above, so
a chest is optional. Hoppers and pipes can extract from any side but cannot insert.

While active, the machine renders a particle effect along **only the perimeter of
its actual harvesting square** (the configured range), so the working area is
visible at a glance. No particles mark the interior.

Horizontal placement faces the player. Existing six-way `facing` states remain
supported for save compatibility. Upgrade blocks one below affect speed and two
below affect range, using the existing configuration values. The machine harvests
plants on its own Y level and observes all resource limits.

Craft with `IHI / CRC / IPI`: four iron ingots, two copper ingots, a hopper,
redstone and a piston. Breaking it drops the machine with its stored contents,
not its upgrade blocks.

Editable project: `art/blockbench/ore_harvester.bbmodel`. Construction recipe:
`art/build_harvester.js` prefixed with `const machineMode = 'idle';` (also
`'running'`, `'blocked'`, `'paused'`). Export each Java Block model into
`models/block/harvester/`, then run `python3 art/install_harvester.py` to install
the exported textures, the GUI artwork and the facing/mode blockstates.

## Compatibility

The `orebushes` mod ID and all existing item/block registry IDs are unchanged.
Only visible names and models change. Existing plants gain the new `harvests=0`
property on load and start with a fresh finite lifetime; they are not deleted.
Breaking a plant returns one starter seed, so automation using breaking loot can
recover and relocate plants.

## Art and verification

- Editable projects: `art/blockbench/<resource>.bbmodel` (23 files).
- Blockbench construction recipe: `art/build_plant.js`. Prefix its executable
  body with `const plantId = 'coal'; const growthStage = 3;` in a Java Block
  project. Stages are `0`, `1`, `2`, `3`, and `'spent'`. Strip the introductory
  comment when using the MCP eval tool. Each rebuild is an undoable edit.
- Export each stage with the Java Block codec into
  `src/main/resources/assets/orebushes/models/block/plants/`. Export the mature
  editable project into `art/blockbench/` using the project codec.
- `python3 art/install_exports.py` extracts the exported Blockbench textures,
  derives the particle sprites, writes original sounds (Pillow + ffmpeg), and
  normalizes model metadata for Minecraft 1.19. Geometry is not generated by Python.
- `bash gradlew runData` regenerates all state variants, item models, translations,
  recipes and loot tables without replacing exported geometry with cross models.
- `python3 art/validate_assets.py` checks 23 distinct silhouettes, 115 models,
  1472 state variants, references, sound assets, finite loot tables and recipes.
- `bash gradlew runGameTestServer` exercises finite lifetime for all 23 plants,
  spent/immature loot, bone meal and dimension restrictions, and automated
  harvesting with a full inventory and after exhaustion.
- `bash gradlew build` builds the mod. Use JDK 17 with ForgeGradle 5/Gradle 7.4.

Client visual/audio checks: view each starter in the creative inventory, place
the plant on its listed substrate/dimension, advance age with `/setblock` while
preserving `harvests`, and inspect growth stages, particles, subtitles and spent
models. Automated/server tests do not replace a complete client visual playtest.
