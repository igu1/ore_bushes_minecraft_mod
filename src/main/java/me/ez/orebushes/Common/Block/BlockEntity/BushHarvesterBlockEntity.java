package me.ez.orebushes.Common.Block.BlockEntity;

import me.ez.orebushes.Init;
import me.ez.orebushes.Util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class BushHarvesterBlockEntity extends BlockEntity {

    private int tick;

    public BushHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(Init.BUSH_HARVESTER_BLOCK_ENTITY.get(), pos, state);
    }

    public static <E extends BlockEntity> void Ticker(Level level, BlockPos pos, BlockState state, E e) {
        if (!level.isClientSide) {
            BushHarvesterBlockEntity bushHarvesterBlockEntity = (BushHarvesterBlockEntity) e;
            bushHarvesterBlockEntity.tick++;
            int SPEED = getSpeed(level, pos, state, bushHarvesterBlockEntity);
            if (bushHarvesterBlockEntity.tick > SPEED * 20) {
                bushHarvesterBlockEntity.tick = 0;
                try {
                    if (level.getBlockEntity(pos.above()) != null) {
                        level.getBlockEntity(pos.above()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                            bushHarvesterBlockEntity.work(handler, level, pos, state, e);
                        });
                    }
                } catch (Exception ex) {
                    System.out.println("Error Found: " + ex);
                }
            }
        }
    }

    private <E extends BlockEntity> void work(IItemHandler handler, Level level, BlockPos pos, BlockState state, E e) {
        int RANGE = getRange(level, pos, state, (BushHarvesterBlockEntity) e);
        for (int x = -RANGE; x <= RANGE; x++) {
            for (int z = -RANGE; z <= RANGE; z++) {
                BlockPos position = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
                BlockState blockState = level.getBlockState(position);
                if (blockState.getBlock() instanceof BushBlock bush) {
                    if (bush.defaultBlockState().hasProperty(BlockStateProperties.AGE_3)) {
                        int AGE = blockState.getValue(BlockStateProperties.AGE_3);
                        if (AGE > 1) {
                            try {
                                List<ItemStack> drops = blockState.getBlock().getDrops(blockState, (ServerLevel) level, position, null);
                                drops.forEach(drop -> {
                                    if (InventoryUtil.hasSpace(handler, state, level, drop)){
                                        BlockState changed_blockstate = blockState.setValue(BlockStateProperties.AGE_3, Integer.valueOf(1));                                level.setBlock(position, changed_blockstate, 2);
                                        level.setBlock(position, changed_blockstate, 1);
                                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, blockState);
                                        InventoryUtil.addItemToInventory(handler, drop.getItem(), drop.getCount());
                                        makeParticle(level, pos, position);
                                    }
                                });
                                break;
                            } catch (Exception exception){
                                System.out.println("Error Found!");
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeParticle(Level level, BlockPos pos, BlockPos bush_pos) {
        for (int x = 0; x < 10; x++) {
            level.addParticle(ParticleTypes.COMPOSTER, bush_pos.getX(), bush_pos.getY(), bush_pos.getZ(), 1D, 1D, 1D);
        }
    }

    private static <E extends BlockEntity> int getSpeed(Level level, BlockPos pos, BlockState state, BushHarvesterBlockEntity e) {
        BlockPos pos1 = pos.below();
        if (level.getBlockState(pos1).is(Blocks.DIAMOND_BLOCK)) {
            return 1;
        }else if (level.getBlockState(pos1).is(Blocks.GOLD_BLOCK)){
            return 20;
        }else if (level.getBlockState(pos1).is(Blocks.IRON_BLOCK)){
            return 30;
        }else{
            return 40;
        }
    }

    private static <E extends BlockEntity> int getRange(Level level, BlockPos pos, BlockState state, BushHarvesterBlockEntity e) {
        BlockPos pos1 = pos.below().below();
        if (level.getBlockState(pos1).is(Blocks.DIAMOND_BLOCK)) {
            return 6;
        }else if (level.getBlockState(pos1).is(Blocks.GOLD_BLOCK)){
            return 4;
        }else if (level.getBlockState(pos1).is(Blocks.IRON_BLOCK)){
            return 3;
        }else{
            return 2;
        }
    }
}
