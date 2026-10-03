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
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).lightLevel(state ->
                state.getValue(AGE) == 3 && state.getValue(HARVESTS) < (CloneItemKey == 10 ? 6 : 10)
                        && (CloneItemKey == 5 || CloneItemKey == 9 || CloneItemKey == 10) ? 4 : 0));
        this.getItemByKey = CloneItemKey;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getItem(getItemByKey);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        return harvest(state, level, pos, player, interactionHand);
    }

    @Override
    protected ItemStack getDropForPlant() { return getDropItem(getItemByKey, 1); }

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
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, Items.COAL.getDefaultInstance());
        stackHashMap.put(2, Items.IRON_NUGGET.getDefaultInstance());
        stackHashMap.put(3, Items.GOLD_NUGGET.getDefaultInstance());
        stackHashMap.put(4, Init.EMERALD_NUGGET.get().getDefaultInstance());
        stackHashMap.put(5, Items.REDSTONE.getDefaultInstance());
        stackHashMap.put(6, Items.LAPIS_LAZULI.getDefaultInstance());
        stackHashMap.put(7, Init.DIAMOND_NUGGET.get().getDefaultInstance());
        stackHashMap.put(8, Init.COPPER_NUGGET.get().getDefaultInstance());
        stackHashMap.put(9, Items.AMETHYST_SHARD.getDefaultInstance());
        stackHashMap.put(10, Items.EXPERIENCE_BOTTLE.getDefaultInstance());
        stackHashMap.put(11, Items.ECHO_SHARD.getDefaultInstance());
        stackHashMap.put(12, Items.GOLDEN_APPLE.getDefaultInstance());
        stackHashMap.put(13, Items.SUGAR.getDefaultInstance());
        ItemStack result = stackHashMap.get(ItemKey);
        result.setCount(amount);
        return result;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
