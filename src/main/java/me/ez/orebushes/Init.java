package me.ez.orebushes;

import me.ez.orebushes.Common.Block.BlockEntity.BushHarvesterBlockEntity;
import me.ez.orebushes.Common.Block.BlockItems.BushHarvesterBlockItem;
import me.ez.orebushes.Common.Block.BushHarvester;
import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Common.Bushes.BushBlockItem;
import me.ez.orebushes.Common.Bushes.OreBushEnd;
import me.ez.orebushes.Common.Bushes.OreBushNether;
import me.ez.orebushes.Common.Bushes.OreBushOverWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Init {
    // DeferredRegister.Blocks / .Items coordinate ordering so block holders are
    // bound before the item suppliers dereference them.
    public static final DeferredRegister.Blocks BUSHES = DeferredRegister.createBlocks(Main.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MOD_ID);

    //Items

    //Overworld
    public static final DeferredItem<BushBlockItem> COAL_BUSH_ITEM = bushItem("coal_bush", () -> Init.COAL_BUSH.get());
    public static final DeferredItem<BushBlockItem> IRON_BUSH_ITEM = bushItem("iron_bush", () -> Init.IRON_BUSH.get());
    public static final DeferredItem<BushBlockItem> GOLD_BUSH_ITEM = bushItem("gold_bush", () -> Init.GOLD_BUSH.get());
    public static final DeferredItem<BushBlockItem> EMERALD_BUSH_ITEM = bushItem("emerald_bush", () -> Init.EMERALD_BUSH.get());
    public static final DeferredItem<BushBlockItem> REDSTONE_BUSH_ITEM = bushItem("redstone_bush", () -> Init.REDSTONE_BUSH.get());
    public static final DeferredItem<BushBlockItem> LAPIS_BUSH_ITEM = bushItem("lapis_bush", () -> Init.LAPIS_BUSH.get());
    public static final DeferredItem<BushBlockItem> DIAMOND_BUSH_ITEM = bushItem("diamond_bush", () -> Init.DIAMOND_BUSH.get());
    public static final DeferredItem<BushBlockItem> COPPER_BUSH_ITEM = bushItem("copper_bush", () -> Init.COPPER_BUSH.get());
    //Nether
    public static final DeferredItem<BushBlockItem> QUARTZ_BUSH_ITEM = bushItem("quartz_bush", () -> Init.QUARTZ_BUSH.get());
    public static final DeferredItem<BushBlockItem> GLOWSTONE_BUSH_ITEM = bushItem("glowstone_bush", () -> Init.GLOWSTONE_BUSH.get());
    public static final DeferredItem<BushBlockItem> NETHERITE_BUSH_ITEM = bushItem("netherite_bush", () -> Init.NETHERITE_BUSH.get());

    //New Overworld
    public static final DeferredItem<BushBlockItem> AMETHYST_BUSH_ITEM = bushItem("amethyst_bush", () -> Init.AMETHYST_BUSH.get());
    public static final DeferredItem<BushBlockItem> EXPERIENCE_BUSH_ITEM = bushItem("experience_bush", () -> Init.EXPERIENCE_BUSH.get());
    public static final DeferredItem<BushBlockItem> ECHO_SHARD_BUSH_ITEM = bushItem("echo_shard_bush", () -> Init.ECHO_SHARD_BUSH.get());
    public static final DeferredItem<BushBlockItem> GOLDEN_APPLE_BUSH_ITEM = bushItem("golden_apple_bush", () -> Init.GOLDEN_APPLE_BUSH.get());
    public static final DeferredItem<BushBlockItem> SUGAR_BUSH_ITEM = bushItem("sugar_bush", () -> Init.SUGAR_BUSH.get());

    //New Nether
    public static final DeferredItem<BushBlockItem> ANCIENT_DEBRIS_BUSH_ITEM = bushItem("ancient_debris_bush", () -> Init.ANCIENT_DEBRIS_BUSH.get());
    public static final DeferredItem<BushBlockItem> BLAZE_BUSH_ITEM = bushItem("blaze_bush", () -> Init.BLAZE_BUSH.get());

    //End
    public static final DeferredItem<BushBlockItem> ENDER_PEARL_BUSH_ITEM = bushItem("ender_pearl_bush", () -> Init.ENDER_PEARL_BUSH.get());
    public static final DeferredItem<BushBlockItem> ENDER_EYE_BUSH_ITEM = bushItem("ender_eye_bush", () -> Init.ENDER_EYE_BUSH.get());
    public static final DeferredItem<BushBlockItem> CHORUS_BUSH_ITEM = bushItem("chorus_bush", () -> Init.CHORUS_BUSH.get());
    public static final DeferredItem<BushBlockItem> SHULKER_SHELL_BUSH_ITEM = bushItem("shulker_shell_bush", () -> Init.SHULKER_SHELL_BUSH.get());
    public static final DeferredItem<BushBlockItem> DRAGON_BREATH_BUSH_ITEM = bushItem("dragon_breath_bush", () -> Init.DRAGON_BREATH_BUSH.get());

    /** Registers a bush seed item through {@link DeferredRegister.Items#registerItem}, which stamps the item id. */
    private static DeferredItem<BushBlockItem> bushItem(String name, Supplier<? extends Block> block) {
        return ITEMS.registerItem(name, props -> new BushBlockItem(block.get(), props), Item.Properties::new);
    }

    //--------------------------------------------------------------------------------


    //Blocks

    //OverWorld
    public static final DeferredBlock<OreBushOverWorld> COAL_BUSH = overworldBush("coal_bush_stage", 1);
    public static final DeferredBlock<OreBushOverWorld> IRON_BUSH = overworldBush("iron_bush_stage", 2);
    public static final DeferredBlock<OreBushOverWorld> GOLD_BUSH = overworldBush("gold_bush_stage", 3);
    public static final DeferredBlock<OreBushOverWorld> EMERALD_BUSH = overworldBush("emerald_bush_stage", 4);
    public static final DeferredBlock<OreBushOverWorld> REDSTONE_BUSH = overworldBush("redstone_bush_stage", 5);
    public static final DeferredBlock<OreBushOverWorld> LAPIS_BUSH = overworldBush("lapis_bush_stage", 6);
    public static final DeferredBlock<OreBushOverWorld> DIAMOND_BUSH = overworldBush("diamond_bush_stage", 7);
    public static final DeferredBlock<OreBushOverWorld> COPPER_BUSH = overworldBush("copper_bush_stage", 8);

    //Nether
    public static final DeferredBlock<OreBushNether> QUARTZ_BUSH = netherBush("quartz_bush_stage", 1);
    public static final DeferredBlock<OreBushNether> GLOWSTONE_BUSH = netherBush("glowstone_bush_stage", 2);
    public static final DeferredBlock<OreBushNether> NETHERITE_BUSH = netherBush("netherite_bush_stage", 3);

    //New Overworld
    public static final DeferredBlock<OreBushOverWorld> AMETHYST_BUSH = overworldBush("amethyst_bush_stage", 9);
    public static final DeferredBlock<OreBushOverWorld> EXPERIENCE_BUSH = overworldBush("experience_bush_stage", 10);
    public static final DeferredBlock<OreBushOverWorld> ECHO_SHARD_BUSH = overworldBush("echo_shard_bush_stage", 11);
    public static final DeferredBlock<OreBushOverWorld> GOLDEN_APPLE_BUSH = overworldBush("golden_apple_bush_stage", 12);
    public static final DeferredBlock<OreBushOverWorld> SUGAR_BUSH = overworldBush("sugar_bush_stage", 13);

    //New Nether
    public static final DeferredBlock<OreBushNether> ANCIENT_DEBRIS_BUSH = netherBush("ancient_debris_bush_stage", 4);
    public static final DeferredBlock<OreBushNether> BLAZE_BUSH = netherBush("blaze_bush_stage", 5);

    //End
    public static final DeferredBlock<OreBushEnd> ENDER_PEARL_BUSH = endBush("ender_pearl_bush_stage", 1);
    public static final DeferredBlock<OreBushEnd> ENDER_EYE_BUSH = endBush("ender_eye_bush_stage", 2);
    public static final DeferredBlock<OreBushEnd> CHORUS_BUSH = endBush("chorus_bush_stage", 3);
    public static final DeferredBlock<OreBushEnd> SHULKER_SHELL_BUSH = endBush("shulker_shell_bush_stage", 4);
    public static final DeferredBlock<OreBushEnd> DRAGON_BREATH_BUSH = endBush("dragon_breath_bush_stage", 5);

    /**
     * Registers a bush through {@link DeferredRegister.Blocks#registerBlock}, which stamps the
     * block id onto its properties. Minecraft 26.1 requires that id during construction.
     */
    private static DeferredBlock<OreBushOverWorld> overworldBush(String name, int variant) {
        return BUSHES.registerBlock(name, props -> new OreBushOverWorld(props, variant),
                () -> OreBushOverWorld.createProperties(variant));
    }

    private static DeferredBlock<OreBushNether> netherBush(String name, int variant) {
        return BUSHES.registerBlock(name, props -> new OreBushNether(props, variant),
                () -> OreBushNether.createProperties(variant));
    }

    private static DeferredBlock<OreBushEnd> endBush(String name, int variant) {
        return BUSHES.registerBlock(name, props -> new OreBushEnd(props, variant),
                () -> OreBushEnd.createProperties(variant));
    }

    public static final DeferredBlock<Block> BUSH_HARVESTER = BLOCKS.registerBlock("bushharvester",
            BushHarvester::new, BushHarvester::createProperties);

    public static final DeferredItem<BushHarvesterBlockItem> BUSH_HARVESTER_BLOCK_ITEM = ITEMS.registerItem("bushharvester",
            BushHarvesterBlockItem::new, Item.Properties::new);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Main.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BushHarvesterBlockEntity>> BUSH_HARVESTER_BLOCK_ENTITY = BLOCK_ENTITY.register("bushharvester", () -> new BlockEntityType<>(BushHarvesterBlockEntity::new, BUSH_HARVESTER.get()));

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Main.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<BushHarvesterMenu>> BUSH_HARVESTER_MENU =
            MENUS.register("bushharvester", () -> IMenuTypeExtension.create(BushHarvesterMenu::new));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ORE_BUSHES_TAB = CREATIVE_TABS.register("ore_bushes",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.orebushes"))
                    .icon(() -> new ItemStack(DIAMOND_BUSH_ITEM.get()))
                    .displayItems(Init::addTabContents)
                    .build());

    /** Fills the Ore Bushes tab with the harvester and every seed. */
    private static void addTabContents(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(BUSH_HARVESTER_BLOCK_ITEM.get());
        ITEMS.getEntries().forEach(holder -> {
            if (holder.get() instanceof BushBlockItem) output.accept(holder.get());
        });
    }

}
