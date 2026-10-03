package me.ez.orebushes.Common.Bushes;

import me.ez.orebushes.Config;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

/** Presentation and progression shared by hand harvesting, automation and tooltips. */
public enum ResourcePlantProfile {
    COAL("coal", "Cinder Fern", 1, Blocks.GRASS_BLOCK, "Overworld; grass or farmland"),
    IRON("iron", "Ferric Reed", 2, Blocks.FARMLAND, "Overworld; farmland"),
    GOLD("gold", "Gilded Lotus", 2, Blocks.FARMLAND, "Overworld; farmland"),
    EMERALD("emerald", "Verdant Spire", 3, Blocks.MOSS_BLOCK, "Overworld; moss"),
    REDSTONE("redstone", "Pulse Vine", 2, Blocks.FARMLAND, "Overworld; farmland"),
    LAPIS("lapis", "Azure Fan", 2, Blocks.FARMLAND, "Overworld; farmland"),
    DIAMOND("diamond", "Prismatic Crown", 3, Blocks.DEEPSLATE, "Overworld; deepslate below Y 0"),
    COPPER("copper", "Patina Frond", 1, Blocks.GRASS_BLOCK, "Overworld; grass or farmland"),
    AMETHYST("amethyst", "Chiming Cluster", 2, Blocks.AMETHYST_BLOCK, "Overworld; amethyst block"),
    EXPERIENCE("experience", "Insight Cap", 3, Blocks.MOSS_BLOCK, "Overworld; moss"),
    ECHO_SHARD("echo_shard", "Echo Tendril", 4, Blocks.SCULK, "Overworld; sculk below Y 0"),
    GOLDEN_APPLE("golden_apple", "Aureate Bonsai", 4, Blocks.MOSS_BLOCK, "Overworld; moss"),
    SUGAR("sugar", "Frosted Cane", 1, Blocks.GRASS_BLOCK, "Overworld; grass or farmland"),
    QUARTZ("quartz", "Ivory Thorn", 2, Blocks.NETHERRACK, "Nether; netherrack"),
    GLOWSTONE("glowstone", "Lantern Bloom", 2, Blocks.SOUL_SOIL, "Nether; soul soil"),
    NETHERITE("netherite", "Obsidian Heart", 4, Blocks.BASALT, "Nether; basalt below Y 32"),
    ANCIENT_DEBRIS("ancient_debris", "Relic Knuckle", 4, Blocks.BASALT, "Nether; basalt below Y 32"),
    BLAZE("blaze", "Ember Antler", 3, Blocks.MAGMA_BLOCK, "Nether; magma block"),
    ENDER_PEARL("ender_pearl", "Rift Pod", 3, Blocks.END_STONE, "End; end stone"),
    ENDER_EYE("ender_eye", "Watcher Orchid", 4, Blocks.END_STONE, "End; end stone"),
    CHORUS("chorus", "Chorus Candelabra", 2, Blocks.END_STONE, "End; end stone"),
    SHULKER_SHELL("shulker_shell", "Shell Rosette", 4, Blocks.PURPUR_BLOCK, "End; purpur block"),
    DRAGON_BREATH("dragon_breath", "Dragon Chalice", 4, Blocks.OBSIDIAN, "End; obsidian");

    public final String id;
    public final String displayName;
    public final int tier;
    public final Block substrate;
    public final String conditions;

    /** Highest harvest value the {@code harvests} blockstate can hold. */
    public static final int MAX_HARVESTS = 15;

    ResourcePlantProfile(String id, String displayName, int tier, Block substrate, String conditions) {
        this.id = id;
        this.displayName = displayName;
        this.tier = tier;
        this.substrate = substrate;
        this.conditions = conditions;
    }

    public int defaultHarvestLimit() { return new int[]{0, 15, 10, 6, 4}[tier]; }

    /**
     * Lifetime harvests for this plant, read from config when available and
     * clamped to the {@code harvests} blockstate range. Datagen and other
     * pre-config contexts fall back to {@link #defaultHarvestLimit()}.
     */
    public int harvestLimit() {
        ForgeConfigSpec.IntValue value = switch (tier) {
            case 1 -> Config.HARVESTS_TIER_1;
            case 2 -> Config.HARVESTS_TIER_2;
            case 3 -> Config.HARVESTS_TIER_3;
            case 4 -> Config.HARVESTS_TIER_4;
            default -> null;
        };
        int limit = defaultHarvestLimit();
        if (value != null && Config.SPEC.isLoaded()) {
            limit = value.get();
        }
        return Math.max(1, Math.min(MAX_HARVESTS, limit));
    }

    public ParticleOptions particle() {
        return me.ez.orebushes.PlantEffects.SPARKS.get(id).get();
    }

    public SoundEvent sound() {
        return me.ez.orebushes.PlantEffects.HARVEST_SOUNDS.get(id).get();
    }

    public static ResourcePlantProfile of(Block block) {
        String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
        for (ResourcePlantProfile profile : values()) {
            if (path.equals(profile.id + "_bush_stage")) return profile;
        }
        throw new IllegalArgumentException("Unknown resource plant: " + path);
    }
}
