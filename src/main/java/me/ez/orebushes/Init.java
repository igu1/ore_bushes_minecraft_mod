package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.BushBlockItem;
import me.ez.orebushes.Common.Bushes.OreBushNether;
import me.ez.orebushes.Common.Bushes.OreBushOverWorld;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Init {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    //Items
    public static final RegistryObject<BushBlockItem> COAL_BUSH_ITEM =ITEMS.register("coal_bush", () -> new BushBlockItem(Init.COAL_BUSH.get()));
    public static final RegistryObject<BushBlockItem> IRON_BUSH_ITEM =ITEMS.register("iron_bush", () -> new BushBlockItem(Init.IRON_BUSH.get()));
    public static final RegistryObject<BushBlockItem> GOLD_BUSH_ITEM =ITEMS.register("gold_bush", () -> new BushBlockItem(Init.GOLD_BUSH.get()));
    public static final RegistryObject<BushBlockItem> EMERALD_BUSH_ITEM =ITEMS.register("emerald_bush", () -> new BushBlockItem(Init.EMERALD_BUSH.get()));
    public static final RegistryObject<BushBlockItem> REDSTONE_BUSH_ITEM =ITEMS.register("redstone_bush", () -> new BushBlockItem(Init.REDSTONE_BUSH.get()));
    public static final RegistryObject<BushBlockItem> LAPIS_BUSH_ITEM =ITEMS.register("lapis_bush", () -> new BushBlockItem(Init.LAPIS_BUSH.get()));
    public static final RegistryObject<BushBlockItem> DIAMOND_BUSH_ITEM =ITEMS.register("diamond_bush", () -> new BushBlockItem(Init.DIAMOND_BUSH.get()));

    public static final RegistryObject<BushBlockItem> QUARTZ_BUSH_ITEM =ITEMS.register("quartz_bush", () -> new BushBlockItem(Init.QUARTZ_BUSH.get()));
    public static final RegistryObject<BushBlockItem> GLOWSTONE_BUSH_ITEM =ITEMS.register("glowstone_bush", () -> new BushBlockItem(Init.GLOWSTONE_BUSH.get()));


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    //Blocks
    public static final RegistryObject<OreBushOverWorld> COAL_BUSH = BLOCKS.register("coal_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> IRON_BUSH = BLOCKS.register("iron_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> GOLD_BUSH = BLOCKS.register("gold_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> EMERALD_BUSH = BLOCKS.register("emerald_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> REDSTONE_BUSH = BLOCKS.register("redstone_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> LAPIS_BUSH = BLOCKS.register("lapis_bush_stage", OreBushOverWorld::new);
    public static final RegistryObject<OreBushOverWorld> DIAMOND_BUSH = BLOCKS.register("diamond_bush_stage", OreBushOverWorld::new);

    public static final RegistryObject<OreBushNether> QUARTZ_BUSH = BLOCKS.register("quartz_bush_stage", OreBushNether::new);
    public static final RegistryObject<OreBushNether> GLOWSTONE_BUSH = BLOCKS.register("glowstone_bush_stage", OreBushNether::new);



}
