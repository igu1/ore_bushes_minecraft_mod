package me.ez.orebushes.Events;

import me.ez.orebushes.Config;
import me.ez.orebushes.Init;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;

public class VillagerTradeHandler {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (!Config.ENABLE_VILLAGER_TRADES.get() || event.getType() != VillagerProfession.FARMER) {
            return;
        }

        List<VillagerTrades.ItemListing> level1 = event.getTrades().get(1);
        List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
        List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);

        level1.add(seed(Init.COAL_BUSH_ITEM.get(), 1, 6, 8, 2));
        level1.add(seed(Init.IRON_BUSH_ITEM.get(), 2, 6, 8, 5));

        level2.add(seed(Init.COPPER_BUSH_ITEM.get(), 2, 6, 6, 5));
        level2.add(seed(Init.REDSTONE_BUSH_ITEM.get(), 3, 4, 6, 10));
        level2.add(seed(Init.SUGAR_BUSH_ITEM.get(), 2, 6, 6, 5));

        level3.add(seed(Init.GOLD_BUSH_ITEM.get(), 4, 4, 4, 15));
        level3.add(seed(Init.LAPIS_BUSH_ITEM.get(), 4, 4, 4, 15));
        level3.add(seed(Init.EMERALD_BUSH_ITEM.get(), 6, 3, 3, 20));

        level4.add(seed(Init.DIAMOND_BUSH_ITEM.get(), 10, 3, 3, 30));
        level4.add(seed(Init.AMETHYST_BUSH_ITEM.get(), 6, 3, 3, 20));
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (!Config.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }
        event.getRareTrades().add(seed(Init.NETHERITE_BUSH_ITEM.get(), 24, 1, 2, 50));
        event.getRareTrades().add(seed(Init.ENDER_PEARL_BUSH_ITEM.get(), 12, 1, 3, 35));
        event.getRareTrades().add(seed(Init.SHULKER_SHELL_BUSH_ITEM.get(), 16, 1, 2, 40));
        event.getRareTrades().add(seed(Init.DRAGON_BREATH_BUSH_ITEM.get(), 16, 1, 2, 40));
    }

    private static VillagerTrades.ItemListing seed(Item seed, int emeraldCost, int count, int maxUses, int xp) {
        me.ez.orebushes.Common.Bushes.ResourcePlantProfile profile = me.ez.orebushes.Common.Bushes.ResourcePlantProfile.of(
                ((me.ez.orebushes.Common.Bushes.BushBlockItem) seed).getBlock());
        Item catalyst = switch (profile.tier) {
            case 1 -> Items.BONE_MEAL;
            case 2 -> Items.AMETHYST_SHARD;
            case 3 -> Items.ENDER_PEARL;
            default -> Items.ECHO_SHARD;
        };
        return (trader, random) -> new MerchantOffer(
                new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, Math.min(64, emeraldCost * profile.tier)),
                java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(catalyst, profile.tier)),
                new ItemStack(seed, 1),
                profile.tier >= 3 ? 1 : 2,
                xp,
                0.05F);
    }
}
