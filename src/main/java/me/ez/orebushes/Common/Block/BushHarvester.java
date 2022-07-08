package me.ez.orebushes.Common.Block;

import me.ez.orebushes.Common.Block.AbstractContainerBlockEntity.AbstractBlockEntityBlock;
import me.ez.orebushes.Common.Block.BlockEntity.BushHarvesterBlockEntity;
import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BushHarvester extends AbstractBlockEntityBlock {

    public BushHarvester() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(3.5F));
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BushHarvesterBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, Init.BUSH_HARVESTER_BLOCK_ENTITY.get(), BushHarvesterBlockEntity::Ticker);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return Stream.of(
                Block.box(1, 6, 1, 15, 7, 15),
                Block.box(1, 7, 1, 2, 15, 2),
                Block.box(1, 7, 14, 2, 15, 15),
                Block.box(14, 7, 14, 15, 15, 15),
                Block.box(14, 7, 1, 15, 15, 2),
                Block.box(1, 7, 2, 2, 15, 14),
                Block.box(14, 7, 2, 15, 15, 14),
                Block.box(2, 7, 14, 14, 15, 15),
                Block.box(2, 7, 1, 14, 15, 2),
                Block.box(0, 15, 0, 16, 16, 16),
                Block.box(0, 0, 0, 16, 6, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState blockState, boolean b) {}
}
