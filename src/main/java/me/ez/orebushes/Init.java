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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Init {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Main.MOD_ID);
    //Items

    //Overworld
    public static final DeferredHolder<Item, BushBlockItem> COAL_BUSH_ITEM =ITEMS.register("coal_bush", () -> new BushBlockItem(Init.COAL_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> IRON_BUSH_ITEM =ITEMS.register("iron_bush", () -> new BushBlockItem(Init.IRON_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> GOLD_BUSH_ITEM =ITEMS.register("gold_bush", () -> new BushBlockItem(Init.GOLD_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> EMERALD_BUSH_ITEM =ITEMS.register("emerald_bush", () -> new BushBlockItem(Init.EMERALD_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> REDSTONE_BUSH_ITEM =ITEMS.register("redstone_bush", () -> new BushBlockItem(Init.REDSTONE_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> LAPIS_BUSH_ITEM =ITEMS.register("lapis_bush", () -> new BushBlockItem(Init.LAPIS_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> DIAMOND_BUSH_ITEM =ITEMS.register("diamond_bush", () -> new BushBlockItem(Init.DIAMOND_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> COPPER_BUSH_ITEM =ITEMS.register("copper_bush", () -> new BushBlockItem(Init.COPPER_BUSH.get()));
    //Nether
    public static final DeferredHolder<Item, BushBlockItem> QUARTZ_BUSH_ITEM =ITEMS.register("quartz_bush", () -> new BushBlockItem(Init.QUARTZ_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> GLOWSTONE_BUSH_ITEM =ITEMS.register("glowstone_bush", () -> new BushBlockItem(Init.GLOWSTONE_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> NETHERITE_BUSH_ITEM =ITEMS.register("netherite_bush", () -> new BushBlockItem(Init.NETHERITE_BUSH.get()));

    //New Overworld
    public static final DeferredHolder<Item, BushBlockItem> AMETHYST_BUSH_ITEM =ITEMS.register("amethyst_bush", () -> new BushBlockItem(Init.AMETHYST_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> EXPERIENCE_BUSH_ITEM =ITEMS.register("experience_bush", () -> new BushBlockItem(Init.EXPERIENCE_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> ECHO_SHARD_BUSH_ITEM =ITEMS.register("echo_shard_bush", () -> new BushBlockItem(Init.ECHO_SHARD_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> GOLDEN_APPLE_BUSH_ITEM =ITEMS.register("golden_apple_bush", () -> new BushBlockItem(Init.GOLDEN_APPLE_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> SUGAR_BUSH_ITEM =ITEMS.register("sugar_bush", () -> new BushBlockItem(Init.SUGAR_BUSH.get()));

    //New Nether
    public static final DeferredHolder<Item, BushBlockItem> ANCIENT_DEBRIS_BUSH_ITEM =ITEMS.register("ancient_debris_bush", () -> new BushBlockItem(Init.ANCIENT_DEBRIS_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> BLAZE_BUSH_ITEM =ITEMS.register("blaze_bush", () -> new BushBlockItem(Init.BLAZE_BUSH.get()));

    //End
    public static final DeferredHolder<Item, BushBlockItem> ENDER_PEARL_BUSH_ITEM =ITEMS.register("ender_pearl_bush", () -> new BushBlockItem(Init.ENDER_PEARL_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> ENDER_EYE_BUSH_ITEM =ITEMS.register("ender_eye_bush", () -> new BushBlockItem(Init.ENDER_EYE_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> CHORUS_BUSH_ITEM =ITEMS.register("chorus_bush", () -> new BushBlockItem(Init.CHORUS_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> SHULKER_SHELL_BUSH_ITEM =ITEMS.register("shulker_shell_bush", () -> new BushBlockItem(Init.SHULKER_SHELL_BUSH.get()));
    public static final DeferredHolder<Item, BushBlockItem> DRAGON_BREATH_BUSH_ITEM =ITEMS.register("dragon_breath_bush", () -> new BushBlockItem(Init.DRAGON_BREATH_BUSH.get()));

    //Drops
    public static final DeferredHolder<Item, Item> EMERALD_NUGGET =ITEMS.register("emerald_nugget",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> DIAMOND_NUGGET =ITEMS.register("diamond_nugget",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> NETHERITE_NUGGET =ITEMS.register("netherite_nugget",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> COPPER_NUGGET =ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties()));


    //--------------------------------------------------------------------------------


    public static final DeferredRegister<Block> BUSHES = DeferredRegister.create(Registries.BLOCK, Main.MOD_ID);
    //Blocks

    //OverWorld
    public static final DeferredHolder<Block, OreBushOverWorld> COAL_BUSH = BUSHES.register("coal_bush_stage", () -> new OreBushOverWorld(1));
    public static final DeferredHolder<Block, OreBushOverWorld> IRON_BUSH = BUSHES.register("iron_bush_stage", () -> new OreBushOverWorld(2));
    public static final DeferredHolder<Block, OreBushOverWorld> GOLD_BUSH = BUSHES.register("gold_bush_stage",() -> new OreBushOverWorld(3));
    public static final DeferredHolder<Block, OreBushOverWorld> EMERALD_BUSH = BUSHES.register("emerald_bush_stage",() -> new OreBushOverWorld(4));
    public static final DeferredHolder<Block, OreBushOverWorld> REDSTONE_BUSH = BUSHES.register("redstone_bush_stage",() -> new OreBushOverWorld(5));
    public static final DeferredHolder<Block, OreBushOverWorld> LAPIS_BUSH = BUSHES.register("lapis_bush_stage", () -> new OreBushOverWorld(6));
    public static final DeferredHolder<Block, OreBushOverWorld> DIAMOND_BUSH = BUSHES.register("diamond_bush_stage",() -> new OreBushOverWorld(7));
    public static final DeferredHolder<Block, OreBushOverWorld> COPPER_BUSH = BUSHES.register("copper_bush_stage",() -> new OreBushOverWorld(8));

    //Nether
    public static final DeferredHolder<Block, OreBushNether> QUARTZ_BUSH = BUSHES.register("quartz_bush_stage", () ->  new OreBushNether(1));
    public static final DeferredHolder<Block, OreBushNether> GLOWSTONE_BUSH = BUSHES.register("glowstone_bush_stage", () -> new OreBushNether(2));
    public static final DeferredHolder<Block, OreBushNether> NETHERITE_BUSH = BUSHES.register("netherite_bush_stage", () ->  new OreBushNether(3));

    //New Overworld
    public static final DeferredHolder<Block, OreBushOverWorld> AMETHYST_BUSH = BUSHES.register("amethyst_bush_stage", () -> new OreBushOverWorld(9));
    public static final DeferredHolder<Block, OreBushOverWorld> EXPERIENCE_BUSH = BUSHES.register("experience_bush_stage", () -> new OreBushOverWorld(10));
    public static final DeferredHolder<Block, OreBushOverWorld> ECHO_SHARD_BUSH = BUSHES.register("echo_shard_bush_stage", () -> new OreBushOverWorld(11));
    public static final DeferredHolder<Block, OreBushOverWorld> GOLDEN_APPLE_BUSH = BUSHES.register("golden_apple_bush_stage", () -> new OreBushOverWorld(12));
    public static final DeferredHolder<Block, OreBushOverWorld> SUGAR_BUSH = BUSHES.register("sugar_bush_stage", () -> new OreBushOverWorld(13));

    //New Nether
    public static final DeferredHolder<Block, OreBushNether> ANCIENT_DEBRIS_BUSH = BUSHES.register("ancient_debris_bush_stage", () -> new OreBushNether(4));
    public static final DeferredHolder<Block, OreBushNether> BLAZE_BUSH = BUSHES.register("blaze_bush_stage", () -> new OreBushNether(5));

    //End
    public static final DeferredHolder<Block, OreBushEnd> ENDER_PEARL_BUSH = BUSHES.register("ender_pearl_bush_stage", () -> new OreBushEnd(1));
    public static final DeferredHolder<Block, OreBushEnd> ENDER_EYE_BUSH = BUSHES.register("ender_eye_bush_stage", () -> new OreBushEnd(2));
    public static final DeferredHolder<Block, OreBushEnd> CHORUS_BUSH = BUSHES.register("chorus_bush_stage", () -> new OreBushEnd(3));
    public static final DeferredHolder<Block, OreBushEnd> SHULKER_SHELL_BUSH = BUSHES.register("shulker_shell_bush_stage", () -> new OreBushEnd(4));
    public static final DeferredHolder<Block, OreBushEnd> DRAGON_BREATH_BUSH = BUSHES.register("dragon_breath_bush_stage", () -> new OreBushEnd(5));




    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Main.MOD_ID);
    public static final DeferredHolder<Block, Block> BUSH_HARVESTER = BLOCKS.register("bushharvester", () -> new BushHarvester());

    public static final DeferredHolder<Item, BushHarvesterBlockItem> BUSH_HARVESTER_BLOCK_ITEM =ITEMS.register("bushharvester", BushHarvesterBlockItem::new);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Main.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BushHarvesterBlockEntity>> BUSH_HARVESTER_BLOCK_ENTITY = BLOCK_ENTITY.register("bushharvester", () -> new BlockEntityType<>(BushHarvesterBlockEntity::new, BUSH_HARVESTER.get()));

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Main.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<BushHarvesterMenu>> BUSH_HARVESTER_MENU =
            MENUS.register("bushharvester", () -> IMenuTypeExtension.create(BushHarvesterMenu::new));

}
