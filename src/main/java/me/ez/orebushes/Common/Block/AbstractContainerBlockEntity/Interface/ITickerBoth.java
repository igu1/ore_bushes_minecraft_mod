package me.ez.orebushes.Common.Block.AbstractContainerBlockEntity.Interface;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ITickerBoth {

    <T extends BlockEntity> void doServerSide(Level level, BlockPos pos, BlockState state, T e);

    <T extends BlockEntity> void doClientSide(Level level, BlockPos pos, BlockState state, T e);
}
