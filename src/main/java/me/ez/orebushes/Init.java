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
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Init {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MOD_ID);
    //Items

    //Overworld
    public static final DeferredHolder<Item, BushBlockItem> COAL_BUSH_ITEM = bushItem("coal_bush", () -> Init.COAL_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> IRON_BUSH_ITEM = bushItem("iron_bush", () -> Init.IRON_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> GOLD_BUSH_ITEM = bushItem("gold_bush", () -> Init.GOLD_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> EMERALD_BUSH_ITEM = bushItem("emerald_bush", () -> Init.EMERALD_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> REDSTONE_BUSH_ITEM = bushItem("redstone_bush", () -> Init.REDSTONE_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> LAPIS_BUSH_ITEM = bushItem("lapis_bush", () -> Init.LAPIS_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> DIAMOND_BUSH_ITEM = bushItem("diamond_bush", () -> Init.DIAMOND_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> COPPER_BUSH_ITEM = bushItem("copper_bush", () -> Init.COPPER_BUSH.get());
    //Nether
    public static final DeferredHolder<Item, BushBlockItem> QUARTZ_BUSH_ITEM = bushItem("quartz_bush", () -> Init.QUARTZ_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> GLOWSTONE_BUSH_ITEM = bushItem("glowstone_bush", () -> Init.GLOWSTONE_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> NETHERITE_BUSH_ITEM = bushItem("netherite_bush", () -> Init.NETHERITE_BUSH.get());

    //New Overworld
    public static final DeferredHolder<Item, BushBlockItem> AMETHYST_BUSH_ITEM = bushItem("amethyst_bush", () -> Init.AMETHYST_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> EXPERIENCE_BUSH_ITEM = bushItem("experience_bush", () -> Init.EXPERIENCE_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> ECHO_SHARD_BUSH_ITEM = bushItem("echo_shard_bush", () -> Init.ECHO_SHARD_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> GOLDEN_APPLE_BUSH_ITEM = bushItem("golden_apple_bush", () -> Init.GOLDEN_APPLE_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> SUGAR_BUSH_ITEM = bushItem("sugar_bush", () -> Init.SUGAR_BUSH.get());

    //New Nether
    public static final DeferredHolder<Item, BushBlockItem> ANCIENT_DEBRIS_BUSH_ITEM = bushItem("ancient_debris_bush", () -> Init.ANCIENT_DEBRIS_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> BLAZE_BUSH_ITEM = bushItem("blaze_bush", () -> Init.BLAZE_BUSH.get());

    //End
    public static final DeferredHolder<Item, BushBlockItem> ENDER_PEARL_BUSH_ITEM = bushItem("ender_pearl_bush", () -> Init.ENDER_PEARL_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> ENDER_EYE_BUSH_ITEM = bushItem("ender_eye_bush", () -> Init.ENDER_EYE_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> CHORUS_BUSH_ITEM = bushItem("chorus_bush", () -> Init.CHORUS_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> SHULKER_SHELL_BUSH_ITEM = bushItem("shulker_shell_bush", () -> Init.SHULKER_SHELL_BUSH.get());
    public static final DeferredHolder<Item, BushBlockItem> DRAGON_BREATH_BUSH_ITEM = bushItem("dragon_breath_bush", () -> Init.DRAGON_BREATH_BUSH.get());

    /** Registers a bush seed item; {@code registerItem} stamps the item id. */
    private static DeferredHolder<Item, BushBlockItem> bushItem(String name, java.util.function.Supplier<? extends Block> block) {
        return ITEMS.registerItem(name, properties -> new BushBlockItem(block.get(), properties));
    }


    //--------------------------------------------------------------------------------


    public static final DeferredRegister.Blocks BUSHES = DeferredRegister.createBlocks(Main.MOD_ID);
    //Blocks

    //OverWorld
    public static final DeferredHolder<Block, OreBushOverWorld> COAL_BUSH = overworldBush("coal_bush_stage", 1);
    public static final DeferredHolder<Block, OreBushOverWorld> IRON_BUSH = overworldBush("iron_bush_stage", 2);
    public static final DeferredHolder<Block, OreBushOverWorld> GOLD_BUSH = overworldBush("gold_bush_stage", 3);
    public static final DeferredHolder<Block, OreBushOverWorld> EMERALD_BUSH = overworldBush("emerald_bush_stage", 4);
    public static final DeferredHolder<Block, OreBushOverWorld> REDSTONE_BUSH = overworldBush("redstone_bush_stage", 5);
    public static final DeferredHolder<Block, OreBushOverWorld> LAPIS_BUSH = overworldBush("lapis_bush_stage", 6);
    public static final DeferredHolder<Block, OreBushOverWorld> DIAMOND_BUSH = overworldBush("diamond_bush_stage", 7);
    public static final DeferredHolder<Block, OreBushOverWorld> COPPER_BUSH = overworldBush("copper_bush_stage", 8);

    //Nether
    public static final DeferredHolder<Block, OreBushNether> QUARTZ_BUSH = netherBush("quartz_bush_stage", 1);
    public static final DeferredHolder<Block, OreBushNether> GLOWSTONE_BUSH = netherBush("glowstone_bush_stage", 2);
    public static final DeferredHolder<Block, OreBushNether> NETHERITE_BUSH = netherBush("netherite_bush_stage", 3);

    //New Overworld
    public static final DeferredHolder<Block, OreBushOverWorld> AMETHYST_BUSH = overworldBush("amethyst_bush_stage", 9);
    public static final DeferredHolder<Block, OreBushOverWorld> EXPERIENCE_BUSH = overworldBush("experience_bush_stage", 10);
    public static final DeferredHolder<Block, OreBushOverWorld> ECHO_SHARD_BUSH = overworldBush("echo_shard_bush_stage", 11);
    public static final DeferredHolder<Block, OreBushOverWorld> GOLDEN_APPLE_BUSH = overworldBush("golden_apple_bush_stage", 12);
    public static final DeferredHolder<Block, OreBushOverWorld> SUGAR_BUSH = overworldBush("sugar_bush_stage", 13);

    //New Nether
    public static final DeferredHolder<Block, OreBushNether> ANCIENT_DEBRIS_BUSH = netherBush("ancient_debris_bush_stage", 4);
    public static final DeferredHolder<Block, OreBushNether> BLAZE_BUSH = netherBush("blaze_bush_stage", 5);

    //End
    public static final DeferredHolder<Block, OreBushEnd> ENDER_PEARL_BUSH = endBush("ender_pearl_bush_stage", 1);
    public static final DeferredHolder<Block, OreBushEnd> ENDER_EYE_BUSH = endBush("ender_eye_bush_stage", 2);
    public static final DeferredHolder<Block, OreBushEnd> CHORUS_BUSH = endBush("chorus_bush_stage", 3);
    public static final DeferredHolder<Block, OreBushEnd> SHULKER_SHELL_BUSH = endBush("shulker_shell_bush_stage", 4);
    public static final DeferredHolder<Block, OreBushEnd> DRAGON_BREATH_BUSH = endBush("dragon_breath_bush_stage", 5);

    /** {@code registerBlock} stamps the block id onto its properties. */
    private static DeferredHolder<Block, OreBushOverWorld> overworldBush(String name, int variant) {
        return BUSHES.registerBlock(name, properties -> new OreBushOverWorld(properties, variant),
                OreBushOverWorld.createProperties(variant));
    }

    private static DeferredHolder<Block, OreBushNether> netherBush(String name, int variant) {
        return BUSHES.registerBlock(name, properties -> new OreBushNether(properties, variant),
                OreBushNether.createProperties(variant));
    }

    private static DeferredHolder<Block, OreBushEnd> endBush(String name, int variant) {
        return BUSHES.registerBlock(name, properties -> new OreBushEnd(properties, variant),
                OreBushEnd.createProperties(variant));
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MOD_ID);
    public static final DeferredHolder<Block, Block> BUSH_HARVESTER = BLOCKS.registerBlock("bushharvester",
            props -> new BushHarvester(props), BushHarvester.createProperties());

    public static final DeferredHolder<Item, BushHarvesterBlockItem> BUSH_HARVESTER_BLOCK_ITEM =
            ITEMS.registerItem("bushharvester", BushHarvesterBlockItem::new);

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
