<p align="center">
  <img src="art/branding/ore-bushes-banner.png" alt="Ore Bushes — Grow your own treasure" width="100%">
</p>

<h1 align="center">Ore Bushes Mod</h1>

<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/ore-bushes"><img src="art/branding/ore-bushes-icon.png" alt="Ore Bushes icon" width="112"></a>
  <br>
  <a href="https://www.curseforge.com/minecraft/mc-mods/ore-bushes"><b>Download Ore Bushes Mod</b></a>
</p>

<p align="center"><i>“Why mine when you can just shake a bush?”</i> — literally.</p>

## Resource plant redesign (this source tree, Forge 1.19)

All 23 resources now have individually named, textured **3D plants made in Blockbench**, four growth stages and a spent state. Plants have finite harvests, tiered growth, special growing conditions, custom particle sprites and original harvest sounds. The harvester uses the same lifetime limits as manual harvesting.

See **[the resource plant guide](docs/RESOURCE_PLANTS.md)** for current names, recipes, growing conditions, balancing and development checks. The older descriptions below refer to the original releases.

---

##  What Is This?

A quirky and delightful Forge mod that adds a range of **ore-bearing bushes** to Minecraft. Instead of digging deep, you can now stroll through forests (or wherever they spawn) and collect precious ores like:

- Coal, Iron, Gold, Diamond, Redstone, Lapis, Glowstone, Emerald, Quartz, Copper, Netherite… and more!

---

##  Features (Because Bushes Should Be Useful)

- Adds a whole garden of ore bushes — from common ores to rarities.
- Bushes spawn naturally and **very rarely**, with rarity scaled to the element:
  - **Overworld** (on grass): coal, iron, copper, redstone, lapis, gold, emerald, diamond, amethyst, golden apple, sugar, experience, echo shard
  - **Nether** (on netherrack): quartz, glowstone, netherite, blaze, ancient debris
  - The rarer the element, the rarer the bush — a **diamond bush is a genuine treasure**, and a **netherite bush is almost a myth**.
- Forge-compatible and easy to install.
- Works with Minecraft versions 1.17 through 1.19 (including 1.19 snapshot builds). 
- A growing fanbase: **over 31,700 downloads** on CurseForge!: 
  - Amethyst Bush — grows **amethyst shards**
  - Experience Bush — grows **experience bottles** (jackpot!)
  - Echo Shard Bush — deep-dark themed, grows **echo shards**
  - Golden Apple Bush — grows **golden apples**
  - Sugar Bush — grows **sugar**
  - Blaze Bush — Nether, grows **blaze rods**
  - Ancient Debris Bush — Nether, grows **ancient debris**

---

##  Installation (Super Simple)

1. Ensure you have **Minecraft Java Edition** and the **Forge mod loader** installed.
2. Download the latest `orebushes-<version>.jar` from the CurseForge Files section.
3. Drop the `.jar` into your `mods/` folder.
4. Launch the game using the Forge profile and start collecting bush-ore!

---

##  Why 30K+ Downloads?

Who wouldn’t want to replace tedious mining with a touch-and-harvest bush fest?  
Ore Bushes is the perfect blend of lazy creativity and clever convenience—just wander around and nature rewards you. Plus, the variety keeps it fresh.

---

##  A Few Bushy Caveats

- Bushes now spawn naturally **very rarely** in the Overworld and Nether — but since they're so rare, the reliable way to get more is still the crafting table.
- Craft a bush seed from a block of the ore plus wheat seeds, plant it on its listed substrate in the right dimension (see the guide), wait for it to grow, then right-click to harvest. Breaking a plant always returns its seed.
- No advanced automation or modpack-specific customization (yet) — but simplicity can be beautiful.

---

##  Feedback & Contributions

Found a weird bug or have an idea for a new ore bush?  
Open an issue or submit a pull request on GitHub. Help the bush garden flourish!

---

##  Final Thoughts

Mining is overrated. Let Mother Nature (or your code) do the work.  
Ore Bushes: bringing nuggets and smiles—one bush at a time.

> “Cleaning your pickaxe one bush shake at a time.”  

---

##  Branding & Artwork

<p align="center">
  <img src="art/branding/ore-bushes-icon.png" alt="Ore Bushes icon" width="128">
</p>

The Ore Bushes logo set lives in [`art/branding/`](art/branding/):

| Asset | Size | Use |
| --- | --- | --- |
| `ore-bushes-banner.png` | 1600×600 | README header, store pages, social previews |
| `ore-bushes-icon.png` | 512×512 (transparent) | Mod JAR logo, CurseForge/Modrinth avatar, favicon |

- Lettering is original inline 5×7 pixel type — no external fonts are bundled.
- Palette: forest `#0b2426`, leaves `#429b50` / `#77bf55`, gold `#e8b954`, teal `#57e6dc`, amethyst `#b29aff`.
- Editable `*.svg` versions sit next to each PNG.
- Regenerate both from source with `python3 art/branding/generate_logos.py` (requires Pillow).
- The in-game mod list logo is `src/main/resources/logo.png` (referenced by `mods.toml`).
