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

public class OreBushNether extends AbstractModBushBlock {

    private final int getItemByKey;

    public OreBushNether(BlockBehaviour.Properties properties, int CloneItemKey) {
        super(properties);
        this.getItemByKey = CloneItemKey;
    }

    /** Base properties for a variant; the deferred register applies the block id. */
    public static BlockBehaviour.Properties createProperties(int CloneItemKey) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).lightLevel(state ->
                state.getValue(AGE) == 3 && state.getValue(HARVESTS) < (CloneItemKey == 2 ? 10 : 6)
                        && (CloneItemKey == 2 || CloneItemKey == 5) ? 8 : 0);
    }

    /** Constructor used by {@link #codec()} when decoding a block definition. */
    public OreBushNether(int CloneItemKey) {
        this(createProperties(CloneItemKey), CloneItemKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public com.mojang.serialization.MapCodec<net.minecraft.world.level.block.BushBlock> codec() {
        return com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(instance ->
                instance.group(com.mojang.serialization.Codec.INT.fieldOf("variant").forGetter(b -> ((OreBushNether) b).getVariant()))
                        .apply(instance, OreBushNether::new));
    }


    @Override
    public ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return getItem(getItemByKey);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.phys.BlockHitResult hitResult) {
        return harvest(state, level, pos, player, InteractionHand.MAIN_HAND);
    }

    @Override
    protected ItemStack getDropForPlant() { return getDropItem(getItemByKey, 1); }

    @Override
    public ItemStack getItem(int ItemKey) {
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, Init.QUARTZ_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(2, Init.GLOWSTONE_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(3, Init.NETHERITE_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(4, Init.ANCIENT_DEBRIS_BUSH_ITEM.get().getDefaultInstance());
        stackHashMap.put(5, Init.BLAZE_BUSH_ITEM.get().getDefaultInstance());

        return stackHashMap.get(ItemKey);
    }

    @Override
    public ItemStack getDropItem(int ItemKey, int amount) {
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, new ItemStack(Items.QUARTZ, amount));
        stackHashMap.put(2, new ItemStack(Items.GLOWSTONE_DUST, amount));
        stackHashMap.put(3, new ItemStack(Init.NETHERITE_NUGGET.get(), amount));
        stackHashMap.put(4, new ItemStack(Items.ANCIENT_DEBRIS, amount));
        stackHashMap.put(5, new ItemStack(Items.BLAZE_ROD, amount));
        return stackHashMap.get(ItemKey);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack tool, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, tool, willHarvest, fluid);
    }

    public int getVariant() { return getItemByKey; }
}
