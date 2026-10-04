package me.ez.orebushes.Common.Block;

import me.ez.orebushes.Common.Block.AbstractContainerBlockEntity.AbstractBlockEntityBlock;
import me.ez.orebushes.Common.Block.BlockEntity.BushHarvesterBlockEntity;
import me.ez.orebushes.Config;
import me.ez.orebushes.Init;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BushHarvester extends AbstractBlockEntityBlock {

    /** Visual/behaviour state exposed to blockstates, particles and the GUI. */
    public enum OperatingState implements net.minecraft.util.StringRepresentable {
        IDLE, RUNNING, BLOCKED, PAUSED;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(java.util.Locale.ROOT);
        }
    }

    public static final EnumProperty<OperatingState> MODE =
            EnumProperty.create("mode", OperatingState.class);

    public BushHarvester() {
        this(BlockBehaviour.Properties.of().strength(3.5F).sound(SoundType.METAL)
                .lightLevel(state -> state.getValue(MODE) == OperatingState.RUNNING ? 7 : 0));
    }

    public BushHarvester(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(BlockStateProperties.FACING, Direction.NORTH)
                .setValue(MODE, OperatingState.IDLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MODE);
    }

    @Override
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
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
    protected com.mojang.serialization.MapCodec<? extends net.minecraft.world.level.block.BaseEntityBlock> codec() {
        return simpleCodec(BushHarvester::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof MenuProvider provider
                && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(provider, buffer -> buffer.writeBlockPos(pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    /** Renders only the perimeter of the actual harvesting square. */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        OperatingState mode = state.getValue(MODE);
        if (mode == OperatingState.PAUSED) return;
        if (mode == OperatingState.RUNNING && random.nextInt(3) == 0) {
            level.addParticle(ParticleTypes.ELECTRIC_SPARK,
                    pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0, 0.02, 0);
        }
        int range = harvestRange(level, pos);
        int samples = mode == OperatingState.RUNNING ? 4 : 1;
        for (int i = 0; i < samples; i++) {
            int side = random.nextInt(4);
            int offset = random.nextInt(range * 2 + 1) - range;
            double x = pos.getX() + 0.5;
            double z = pos.getZ() + 0.5;
            switch (side) {
                case 0 -> { x += offset; z -= range; }
                case 1 -> { x += offset; z += range; }
                case 2 -> { x -= range; z += offset; }
                default -> { x += range; z += offset; }
            }
            level.addParticle(ParticleTypes.END_ROD, x, pos.getY() + 1.05, z, 0, 0, 0);
        }
    }

    public static int harvestSpeed(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        if (below.is(Blocks.DIAMOND_BLOCK)) return Config.DIAMOND_SECONDS.get();
        if (below.is(Blocks.GOLD_BLOCK)) return Config.GOLD_SECONDS.get();
        if (below.is(Blocks.IRON_BLOCK)) return Config.IRON_SECONDS.get();
        return Config.DEFAULT_SECONDS.get();
    }

    public static int harvestRange(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below().below());
        if (below.is(Blocks.DIAMOND_BLOCK)) return Config.DIAMOND_RANGE.get();
        if (below.is(Blocks.GOLD_BLOCK)) return Config.GOLD_RANGE.get();
        if (below.is(Blocks.IRON_BLOCK)) return Config.IRON_RANGE.get();
        return Config.DEFAULT_RANGE.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof net.minecraft.world.Container container) {
            net.minecraft.world.Containers.dropContents(level, pos, container);
        }
        if (!state.is(newState.getBlock())) level.removeBlockEntity(pos);
    }
}
