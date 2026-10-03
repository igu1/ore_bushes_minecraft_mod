package me.ez.orebushes;

import me.ez.orebushes.Common.Block.BlockEntity.BushHarvesterBlockEntity;
import me.ez.orebushes.Common.Block.BlockItems.BushHarvesterBlockItem;
import me.ez.orebushes.Common.Block.BushHarvester;
import me.ez.orebushes.Common.Menu.BushHarvesterMenu;
import me.ez.orebushes.Common.Bushes.BushBlockItem;
import me.ez.orebushes.Common.Bushes.OreBushEnd;
import me.ez.orebushes.Common.Bushes.OreBushNether;
import me.ez.orebushes.Common.Bushes.OreBushOverWorld;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Init {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    //Items

    //Overworld
    public static final RegistryObject<BushBlockItem> COAL_BUSH_ITEM =ITEMS.register("coal_bush", () -> new BushBlockItem(Init.COAL_BUSH.get()));
    public static final RegistryObject<BushBlockItem> IRON_BUSH_ITEM =ITEMS.register("iron_bush", () -> new BushBlockItem(Init.IRON_BUSH.get()));
    public static final RegistryObject<BushBlockItem> GOLD_BUSH_ITEM =ITEMS.register("gold_bush", () -> new BushBlockItem(Init.GOLD_BUSH.get()));
    public static final RegistryObject<BushBlockItem> EMERALD_BUSH_ITEM =ITEMS.register("emerald_bush", () -> new BushBlockItem(Init.EMERALD_BUSH.get()));
    public static final RegistryObject<BushBlockItem> REDSTONE_BUSH_ITEM =ITEMS.register("redstone_bush", () -> new BushBlockItem(Init.REDSTONE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> LAPIS_BUSH_ITEM =ITEMS.register("lapis_bush", () -> new BushBlockItem(Init.LAPIS_BUSH.get()));
    public static final RegistryObject<BushBlockItem> DIAMOND_BUSH_ITEM =ITEMS.register("diamond_bush", () -> new BushBlockItem(Init.DIAMOND_BUSH.get()));
    public static final RegistryObject<BushBlockItem> COPPER_BUSH_ITEM =ITEMS.register("copper_bush", () -> new BushBlockItem(Init.COPPER_BUSH.get()));
    //Nether
    public static final RegistryObject<BushBlockItem> QUARTZ_BUSH_ITEM =ITEMS.register("quartz_bush", () -> new BushBlockItem(Init.QUARTZ_BUSH.get()));
    public static final RegistryObject<BushBlockItem> GLOWSTONE_BUSH_ITEM =ITEMS.register("glowstone_bush", () -> new BushBlockItem(Init.GLOWSTONE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> NETHERITE_BUSH_ITEM =ITEMS.register("netherite_bush", () -> new BushBlockItem(Init.NETHERITE_BUSH.get()));

    //New Overworld
    public static final RegistryObject<BushBlockItem> AMETHYST_BUSH_ITEM =ITEMS.register("amethyst_bush", () -> new BushBlockItem(Init.AMETHYST_BUSH.get()));
    public static final RegistryObject<BushBlockItem> EXPERIENCE_BUSH_ITEM =ITEMS.register("experience_bush", () -> new BushBlockItem(Init.EXPERIENCE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> ECHO_SHARD_BUSH_ITEM =ITEMS.register("echo_shard_bush", () -> new BushBlockItem(Init.ECHO_SHARD_BUSH.get()));
    public static final RegistryObject<BushBlockItem> GOLDEN_APPLE_BUSH_ITEM =ITEMS.register("golden_apple_bush", () -> new BushBlockItem(Init.GOLDEN_APPLE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> SUGAR_BUSH_ITEM =ITEMS.register("sugar_bush", () -> new BushBlockItem(Init.SUGAR_BUSH.get()));

    //New Nether
    public static final RegistryObject<BushBlockItem> ANCIENT_DEBRIS_BUSH_ITEM =ITEMS.register("ancient_debris_bush", () -> new BushBlockItem(Init.ANCIENT_DEBRIS_BUSH.get()));
    public static final RegistryObject<BushBlockItem> BLAZE_BUSH_ITEM =ITEMS.register("blaze_bush", () -> new BushBlockItem(Init.BLAZE_BUSH.get()));

    //End
    public static final RegistryObject<BushBlockItem> ENDER_PEARL_BUSH_ITEM =ITEMS.register("ender_pearl_bush", () -> new BushBlockItem(Init.ENDER_PEARL_BUSH.get()));
    public static final RegistryObject<BushBlockItem> ENDER_EYE_BUSH_ITEM =ITEMS.register("ender_eye_bush", () -> new BushBlockItem(Init.ENDER_EYE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> CHORUS_BUSH_ITEM =ITEMS.register("chorus_bush", () -> new BushBlockItem(Init.CHORUS_BUSH.get()));
    public static final RegistryObject<BushBlockItem> SHULKER_SHELL_BUSH_ITEM =ITEMS.register("shulker_shell_bush", () -> new BushBlockItem(Init.SHULKER_SHELL_BUSH.get()));
    public static final RegistryObject<BushBlockItem> DRAGON_BREATH_BUSH_ITEM =ITEMS.register("dragon_breath_bush", () -> new BushBlockItem(Init.DRAGON_BREATH_BUSH.get()));

    //Drops
    public static final RegistryObject<Item> EMERALD_NUGGET =ITEMS.register("emerald_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> DIAMOND_NUGGET =ITEMS.register("diamond_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> NETHERITE_NUGGET =ITEMS.register("netherite_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> COPPER_NUGGET =ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));


    //--------------------------------------------------------------------------------


    public static final DeferredRegister<Block> BUSHES = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    //Blocks

    //OverWorld
    public static final RegistryObject<OreBushOverWorld> COAL_BUSH = BUSHES.register("coal_bush_stage", () -> new OreBushOverWorld(1));
    public static final RegistryObject<OreBushOverWorld> IRON_BUSH = BUSHES.register("iron_bush_stage", () -> new OreBushOverWorld(2));
    public static final RegistryObject<OreBushOverWorld> GOLD_BUSH = BUSHES.register("gold_bush_stage",() -> new OreBushOverWorld(3));
    public static final RegistryObject<OreBushOverWorld> EMERALD_BUSH = BUSHES.register("emerald_bush_stage",() -> new OreBushOverWorld(4));
    public static final RegistryObject<OreBushOverWorld> REDSTONE_BUSH = BUSHES.register("redstone_bush_stage",() -> new OreBushOverWorld(5));
    public static final RegistryObject<OreBushOverWorld> LAPIS_BUSH = BUSHES.register("lapis_bush_stage", () -> new OreBushOverWorld(6));
    public static final RegistryObject<OreBushOverWorld> DIAMOND_BUSH = BUSHES.register("diamond_bush_stage",() -> new OreBushOverWorld(7));
    public static final RegistryObject<OreBushOverWorld> COPPER_BUSH = BUSHES.register("copper_bush_stage",() -> new OreBushOverWorld(8));

    //Nether
    public static final RegistryObject<OreBushNether> QUARTZ_BUSH = BUSHES.register("quartz_bush_stage", () ->  new OreBushNether(1));
    public static final RegistryObject<OreBushNether> GLOWSTONE_BUSH = BUSHES.register("glowstone_bush_stage", () -> new OreBushNether(2));
    public static final RegistryObject<OreBushNether> NETHERITE_BUSH = BUSHES.register("netherite_bush_stage", () ->  new OreBushNether(3));

    //New Overworld
    public static final RegistryObject<OreBushOverWorld> AMETHYST_BUSH = BUSHES.register("amethyst_bush_stage", () -> new OreBushOverWorld(9));
    public static final RegistryObject<OreBushOverWorld> EXPERIENCE_BUSH = BUSHES.register("experience_bush_stage", () -> new OreBushOverWorld(10));
    public static final RegistryObject<OreBushOverWorld> ECHO_SHARD_BUSH = BUSHES.register("echo_shard_bush_stage", () -> new OreBushOverWorld(11));
    public static final RegistryObject<OreBushOverWorld> GOLDEN_APPLE_BUSH = BUSHES.register("golden_apple_bush_stage", () -> new OreBushOverWorld(12));
    public static final RegistryObject<OreBushOverWorld> SUGAR_BUSH = BUSHES.register("sugar_bush_stage", () -> new OreBushOverWorld(13));

    //New Nether
    public static final RegistryObject<OreBushNether> ANCIENT_DEBRIS_BUSH = BUSHES.register("ancient_debris_bush_stage", () -> new OreBushNether(4));
    public static final RegistryObject<OreBushNether> BLAZE_BUSH = BUSHES.register("blaze_bush_stage", () -> new OreBushNether(5));

    //End
    public static final RegistryObject<OreBushEnd> ENDER_PEARL_BUSH = BUSHES.register("ender_pearl_bush_stage", () -> new OreBushEnd(1));
    public static final RegistryObject<OreBushEnd> ENDER_EYE_BUSH = BUSHES.register("ender_eye_bush_stage", () -> new OreBushEnd(2));
    public static final RegistryObject<OreBushEnd> CHORUS_BUSH = BUSHES.register("chorus_bush_stage", () -> new OreBushEnd(3));
    public static final RegistryObject<OreBushEnd> SHULKER_SHELL_BUSH = BUSHES.register("shulker_shell_bush_stage", () -> new OreBushEnd(4));
    public static final RegistryObject<OreBushEnd> DRAGON_BREATH_BUSH = BUSHES.register("dragon_breath_bush_stage", () -> new OreBushEnd(5));




    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final RegistryObject<Block> BUSH_HARVESTER = BLOCKS.register("bushharvester", BushHarvester::new);

    public static final RegistryObject<BushHarvesterBlockItem> BUSH_HARVESTER_BLOCK_ITEM =ITEMS.register("bushharvester", BushHarvesterBlockItem::new);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MOD_ID);
    public static final RegistryObject<BlockEntityType<BushHarvesterBlockEntity>> BUSH_HARVESTER_BLOCK_ENTITY = BLOCK_ENTITY.register("bushharvester", () -> BlockEntityType.Builder.of(BushHarvesterBlockEntity::new, BUSH_HARVESTER.get()).build(null));

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);
    public static final RegistryObject<MenuType<BushHarvesterMenu>> BUSH_HARVESTER_MENU =
            MENUS.register("bushharvester", () -> IForgeMenuType.create(BushHarvesterMenu::new));

}
