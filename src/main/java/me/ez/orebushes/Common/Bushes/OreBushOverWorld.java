package me.ez.orebushes.Common.Bushes;

import me.ez.orebushes.Config;
import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.HashMap;

public class OreBushOverWorld extends AbstractModBushBlock {

    private final int getItemByKey;

    public OreBushOverWorld(int CloneItemKey) {
        this(createProperties(CloneItemKey), CloneItemKey);
    }

    /** Base properties for a variant; the deferred register applies the block id. */
    public static BlockBehaviour.Properties createProperties(int CloneItemKey) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel(state ->
                state.getValue(AGE) == 3 && state.getValue(HARVESTS) < (CloneItemKey == 10 ? 6 : 10)
                        && (CloneItemKey == 5 || CloneItemKey == 9 || CloneItemKey == 10) ? 4 : 0);
    }

    public OreBushOverWorld(BlockBehaviour.Properties properties, int CloneItemKey) {
        super(properties);
        this.getItemByKey = CloneItemKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public com.mojang.serialization.MapCodec<net.minecraft.world.level.block.BushBlock> codec() {
        return com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(instance ->
                instance.group(com.mojang.serialization.Codec.INT.fieldOf("variant").forGetter(b -> ((OreBushOverWorld) b).getVariant()))
                        .apply(instance, OreBushOverWorld::new));
    }


    @Override
    public ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        return getItem(getItemByKey);
    }
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.phys.BlockHitResult hitResult) {
        return harvest(state, level, pos, player, InteractionHand.MAIN_HAND);
    }

    @Override
    protected int getVariantKey() { return getItemByKey; }

    @Override
    public ItemStack getItem(int ItemKey) {
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, Init.COAL_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(2, Init.IRON_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(3, Init.GOLD_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(4, Init.EMERALD_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(5, Init.REDSTONE_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(6, Init.LAPIS_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(7, Init.DIAMOND_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(8, Init.COPPER_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(9, Init.AMETHYST_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(10, Init.EXPERIENCE_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(11, Init.ECHO_SHARD_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(12, Init.GOLDEN_APPLE_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(13, Init.SUGAR_BUSH_ITEM.get().getDefaultInstance());
        return stackHashMap.get(ItemKey);
    }

    @Override
    public ItemStack getDropItem(int ItemKey, int amount) {
        // Every plant returns strictly more than its seed core over its lifetime, so
        // a bush is never a loss. Same-item plants yield 2 per harvest (>= 2x the
        // core across their lifetime); iron/gold give an ingot, not a nugget.
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, new ItemStack(Items.COAL, amount));
        stackHashMap.put(2, new ItemStack(Items.RAW_IRON, amount));
        stackHashMap.put(3, new ItemStack(Items.RAW_GOLD, amount));
        stackHashMap.put(4, new ItemStack(Items.EMERALD, amount));
        stackHashMap.put(5, new ItemStack(Items.REDSTONE, 2 * amount));
        stackHashMap.put(6, new ItemStack(Items.LAPIS_LAZULI, amount));
        stackHashMap.put(7, new ItemStack(Items.DIAMOND, amount));
        stackHashMap.put(8, new ItemStack(Items.RAW_COPPER, 2 * amount));
        stackHashMap.put(9, new ItemStack(Items.AMETHYST_SHARD, 2 * amount));
        stackHashMap.put(10, new ItemStack(Items.EXPERIENCE_BOTTLE, 3 * amount));
        stackHashMap.put(11, new ItemStack(Items.ECHO_SHARD, 2 * amount));
        stackHashMap.put(12, new ItemStack(Items.GOLDEN_APPLE, 2 * amount));
        stackHashMap.put(13, new ItemStack(Items.SUGAR, 2 * amount));
        return stackHashMap.get(ItemKey);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    public int getVariant() { return getItemByKey; }
}
