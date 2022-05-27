package me.ez.orebushes.Common.Bushes;

import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.HashMap;

public class OreBushOverWorld extends AbstractModBushBlock {

    private final int getItemByKey;

    public OreBushOverWorld(int CloneItemKey) {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH));
        this.getItemByKey = CloneItemKey;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getItem(getItemByKey);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (i > 1){
            int j = 1 + level.random.nextInt(1);
            popResource(level, pos, new ItemStack(getDropItem(getItemByKey).getItem(), j + (flag ? 1 : 0)));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(AGE, 1), 2);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

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
        return stackHashMap.get(ItemKey);
    }

    @Override
    public ItemStack getDropItem(int ItemKey) {
        HashMap<Integer, ItemStack> stackHashMap = new HashMap<>();
        stackHashMap.put(1, Items.COAL.getDefaultInstance());
        stackHashMap.put(2, Items.IRON_NUGGET.getDefaultInstance());
        stackHashMap.put(3, Items.GOLD_NUGGET.getDefaultInstance());
        stackHashMap.put(4, Init.EMERALD_NUGGET.get().getDefaultInstance());
        stackHashMap.put(5, Items.REDSTONE.getDefaultInstance());
        stackHashMap.put(6, Items.LAPIS_LAZULI.getDefaultInstance());
        stackHashMap.put(7, Init.DIAMOND_NUGGET.get().getDefaultInstance());
        return stackHashMap.get(ItemKey);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.FARMLAND);
    }
}
