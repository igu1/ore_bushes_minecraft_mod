package me.ez.orebushes;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_BONEMEAL;
    public static final ModConfigSpec.BooleanValue ENABLE_FORTUNE_BONUS;
    public static final ModConfigSpec.BooleanValue ENABLE_VILLAGER_TRADES;
    public static final ModConfigSpec.BooleanValue ENABLE_COMPOSTING;
    public static final ModConfigSpec.DoubleValue COMPOST_CHANCE;

    public static final ModConfigSpec.IntValue GROWTH_CHANCE_PERCENT;
    public static final ModConfigSpec.BooleanValue REQUIRE_LIGHT;
    public static final ModConfigSpec.IntValue MIN_LIGHT;

    public static final ModConfigSpec.DoubleValue AMOUNT_MULTIPLIER;
    public static final ModConfigSpec.IntValue MATURE_BONUS;

    public static final ModConfigSpec.IntValue HARVESTS_TIER_1;
    public static final ModConfigSpec.IntValue HARVESTS_TIER_2;
    public static final ModConfigSpec.IntValue HARVESTS_TIER_3;
    public static final ModConfigSpec.IntValue HARVESTS_TIER_4;

    public static final ModConfigSpec.IntValue DIAMOND_SECONDS;
    public static final ModConfigSpec.IntValue GOLD_SECONDS;
    public static final ModConfigSpec.IntValue IRON_SECONDS;
    public static final ModConfigSpec.IntValue DEFAULT_SECONDS;

    public static final ModConfigSpec.IntValue DIAMOND_RANGE;
    public static final ModConfigSpec.IntValue GOLD_RANGE;
    public static final ModConfigSpec.IntValue IRON_RANGE;
    public static final ModConfigSpec.IntValue DEFAULT_RANGE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("General gameplay toggles").push("general");
        ENABLE_BONEMEAL = builder
                .comment("Allow bone meal to advance bush growth")
                .define("enableBonemeal", true);
        ENABLE_FORTUNE_BONUS = builder
                .comment("Allow the Fortune enchantment to increase harvest drops")
                .define("enableFortuneBonus", true);
        ENABLE_VILLAGER_TRADES = builder
                .comment("Add bush seeds to farmer and wandering trader trades")
                .define("enableVillagerTrades", true);
        ENABLE_COMPOSTING = builder
                .comment("Allow bush seeds to be used in a composter")
                .define("enableComposting", true);
        COMPOST_CHANCE = builder
                .comment("Composter fill chance for bush seeds (0.0 - 1.0)")
                .defineInRange("compostChance", 0.3D, 0.0D, 1.0D);
        builder.pop();

        builder.comment("Bush growth behavior").push("growth");
        GROWTH_CHANCE_PERCENT = builder
                .comment("Percent chance per random tick for a bush to advance one growth stage")
                .defineInRange("growthChancePercent", 100, 1, 100);
        REQUIRE_LIGHT = builder
                .comment("Require a minimum light level for bushes to grow")
                .define("requireLight", false);
        MIN_LIGHT = builder
                .comment("Minimum light level required to grow (only used when requireLight is true)")
                .defineInRange("minLight", 9, 0, 15);
        builder.pop();

        builder.comment("Harvest drop tuning").push("harvest");
        AMOUNT_MULTIPLIER = builder
                .comment("Multiplies hand-harvest and Bush Harvester drops")
                .defineInRange("amountMultiplier", 1.0D, 0.1D, 10.0D);
        MATURE_BONUS = builder
                .comment("Extra items dropped when harvesting a fully grown (age 3) bush")
                .defineInRange("matureBonus", 1, 0, 16);
        HARVESTS_TIER_1 = builder
                .comment("Lifetime harvests for tier 1 plants (max 15)")
                .defineInRange("harvestsTier1", 15, 1, 15);
        HARVESTS_TIER_2 = builder
                .comment("Lifetime harvests for tier 2 plants (max 15)")
                .defineInRange("harvestsTier2", 10, 1, 15);
        HARVESTS_TIER_3 = builder
                .comment("Lifetime harvests for tier 3 plants (max 15)")
                .defineInRange("harvestsTier3", 6, 1, 15);
        HARVESTS_TIER_4 = builder
                .comment("Lifetime harvests for tier 4 plants (max 15)")
                .defineInRange("harvestsTier4", 4, 1, 15);
        builder.pop();

        builder.comment("Bush Harvester speed and range by the block placed below it").push("harvester");
        DIAMOND_SECONDS = builder.comment("Seconds per harvest with a diamond block below").defineInRange("diamondSeconds", 1, 1, 3600);
        GOLD_SECONDS = builder.comment("Seconds per harvest with a gold block below").defineInRange("goldSeconds", 20, 1, 3600);
        IRON_SECONDS = builder.comment("Seconds per harvest with an iron block below").defineInRange("ironSeconds", 30, 1, 3600);
        DEFAULT_SECONDS = builder.comment("Seconds per harvest with no upgrade block below").defineInRange("defaultSeconds", 40, 1, 3600);

        DIAMOND_RANGE = builder.comment("Harvest radius with a diamond block two below").defineInRange("diamondRange", 8, 1, 32);
        GOLD_RANGE = builder.comment("Harvest radius with a gold block two below").defineInRange("goldRange", 4, 1, 32);
        IRON_RANGE = builder.comment("Harvest radius with an iron block two below").defineInRange("ironRange", 3, 1, 32);
        DEFAULT_RANGE = builder.comment("Harvest radius with no upgrade block two below").defineInRange("defaultRange", 2, 1, 32);
        builder.pop();

        SPEC = builder.build();
    }
}
