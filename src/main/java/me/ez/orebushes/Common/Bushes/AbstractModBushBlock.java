package me.ez.orebushes.Common.Bushes;

import me.ez.orebushes.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("deprecation")
public abstract class AbstractModBushBlock extends BushBlock implements BonemealableBlock {

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final IntegerProperty HARVESTS = IntegerProperty.create("harvests", 0, ResourcePlantProfile.MAX_HARVESTS);

    public AbstractModBushBlock(Properties p_51021_) {
        super(p_51021_);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(HARVESTS, 0));
    }

    public ResourcePlantProfile profile() { return ResourcePlantProfile.of(this); }

    public boolean isExhausted(BlockState state) {
        return state.getValue(HARVESTS) >= profile().harvestLimit();
    }

    /** Soil the plant is allowed to sit on (its substrate, plus farmland for tier 1). */
    public boolean isSubstrate(BlockState soil) {
        ResourcePlantProfile profile = profile();
        return soil.is(profile.substrate)
                || profile.tier == 1 && soil.is(net.minecraft.world.level.block.Blocks.FARMLAND);
    }

    /** Dimension the plant belongs to. Unknown readers (e.g. worldgen) are allowed. */
    public boolean dimensionMatches(BlockGetter getter) {
        return !(getter instanceof Level level) || dimensionMatches(level);
    }

    public boolean dimensionMatches(Level level) {
        ResourcePlantProfile profile = profile();
        if (profile.ordinal() >= ResourcePlantProfile.ENDER_PEARL.ordinal())
            return level.dimension().equals(Level.END);
        if (profile.ordinal() >= ResourcePlantProfile.QUARTZ.ordinal())
            return level.dimension().equals(Level.NETHER);
        return level.dimension().equals(Level.OVERWORLD);
    }

    public boolean canGrow(Level level, BlockPos pos, BlockState state) {
        if (isExhausted(state)) return false;
        ResourcePlantProfile profile = profile();
        BlockState soil = level.getBlockState(pos.below());
        boolean depth = profile == ResourcePlantProfile.DIAMOND || profile == ResourcePlantProfile.ECHO_SHARD
                ? pos.getY() < 0 : profile == ResourcePlantProfile.NETHERITE || profile == ResourcePlantProfile.ANCIENT_DEBRIS
                ? pos.getY() < 32 : true;
        return dimensionMatches(level) && isSubstrate(soil) && depth;
    }

    /** Soil/dimension rules for planting or surviving, shared by placement and worldgen. */
    public boolean canPlaceOn(BlockGetter getter, BlockState soil) {
        if (getter instanceof Level level) {
            // Player placement is strict: the listed substrate in the listed dimension.
            return isSubstrate(soil) && dimensionMatches(level);
        }
        // Natural generation keeps the historic per-family soils (biased toward the
        // wild plants' own base blocks so rare discoveries still appear).
        ResourcePlantProfile profile = profile();
        if (profile.ordinal() >= ResourcePlantProfile.ENDER_PEARL.ordinal())
            return soil.is(net.minecraft.world.level.block.Blocks.END_STONE) || soil.is(profile.substrate);
        if (profile.ordinal() >= ResourcePlantProfile.QUARTZ.ordinal())
            return soil.is(net.minecraft.world.level.block.Blocks.NETHERRACK) || soil.is(profile.substrate);
        return soil.is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)
                || soil.is(net.minecraft.world.level.block.Blocks.FARMLAND)
                || soil.is(profile.substrate);
    }

    @Override
    protected boolean mayPlaceOn(BlockState soil, BlockGetter getter, BlockPos pos) {
        return canPlaceOn(getter, soil);
    }

    @Override
    public boolean canSurvive(BlockState state, net.minecraft.world.level.LevelReader level, BlockPos pos) {
        return canPlaceOn(level, level.getBlockState(pos.below()));
    }

    /**
     * One harvest of a ripe plant. Every plant overrides this (via its family) to
     * return strictly more value than its seed core over its lifetime, so a plant
     * is never a loss.
     */
    protected ItemStack getDropForPlant() { return getDropItem(getVariantKey(), 1); }

    public ItemStack harvestDrop(BlockState state) {
        if (state.getValue(AGE) != MAX_AGE || isExhausted(state)) return ItemStack.EMPTY;
        ItemStack drop = getDropForPlant();
        if (drop.isEmpty()) return ItemStack.EMPTY;
        drop.setCount(Math.max(1, drop.getCount()) * getDropCount());
        return drop;
    }

    /** The variant key used by {@link #getDropItem(int, int)}; set by each plant family. */
    protected abstract int getVariantKey();

    /**
     * Items per harvest. Tiers 1-2 get a configurable multiplier; tiers 3-4 keep a
     * fixed amount so rare resources stay bounded, but their per-harvest yield is
     * already set higher than the core so they still profit over their lifetime.
     */
    protected int getDropCount() {
        return profile().tier >= 3 ? 1 : Math.min(3,
                Math.max(1, (int) Math.round((1 + Config.MATURE_BONUS.get()) * Config.AMOUNT_MULTIPLIER.get())));
    }

    /** What a ripe plant yields from the automated Ore Harvester. */
    public ItemStack machineDrop() { return harvestDrop(defaultBlockState().setValue(AGE, MAX_AGE)); }

    public void finishHarvest(ServerLevel level, BlockPos pos, BlockState state) {
        finishHarvest(level, pos, state, true);
    }

    /** Machines keep the normal harvest transition and particles, without audio. */
    public void finishHarvest(ServerLevel level, BlockPos pos, BlockState state, boolean playSound) {
        if (state.getValue(AGE) != MAX_AGE || isExhausted(state) || !level.getBlockState(pos).equals(state)) return;
        // Harvesting sets the plant back one growth stage (ripe -> budding) and
        // advances the lifetime counter, so it must regrow before the next harvest.
        int nextAge = Math.max(0, state.getValue(AGE) - 1);
        level.setBlock(pos, state
                .setValue(AGE, nextAge)
                .setValue(HARVESTS, state.getValue(HARVESTS) + 1), 3);
        if (playSound) {
            level.playSound(null, pos, profile().sound(), SoundSource.BLOCKS, 0.65F, 0.9F + level.getRandom().nextFloat() * 0.2F);
        }
        level.sendParticles(profile().particle(), pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, 12, 0.25, 0.3, 0.25, 0.02);
    }

    public InteractionResult harvest(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (state.getValue(AGE) < MAX_AGE || isExhausted(state)) return InteractionResult.PASS;
        if (!level.isClientSide()) {
            ItemStack drop = harvestDrop(state);
            if (profile().tier < 3 && Config.ENABLE_FORTUNE_BONUS.get()) {
                var fortuneHolder = level.registryAccess()
                        .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                        .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.FORTUNE);
                int fortune = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(
                        fortuneHolder, player.getItemInHand(hand));
                if (fortune > 0 && level.getRandom().nextInt(fortune + 1) > 0) drop.setCount(Math.min(3, drop.getCount() + 1));
            }
            popResource(level, pos, drop);
            finishHarvest((ServerLevel) level, pos, state);
            int remaining = profile().harvestLimit() - state.getValue(HARVESTS) - 1;
            String got = drop.getCount() + "x " + drop.getHoverName().getString();
            ((net.minecraft.server.level.ServerPlayer) player).sendSystemMessage(net.minecraft.network.chat.Component.literal(
                    "Harvested " + got + (remaining == 0
                            ? " — " + profile().displayName + " is spent."
                            : " (" + remaining + " harvests left).")), true);
            if (profile() == ResourcePlantProfile.EXPERIENCE) {
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.NIGHT_VISION, 200));
            } else if (profile() == ResourcePlantProfile.GOLDEN_APPLE) {
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 60));
            }
        }
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (isExhausted(state) || state.getValue(AGE) < 2 || random.nextInt(5) != 0) return;
        level.addParticle(profile().particle(), pos.getX() + 0.2 + random.nextDouble() * 0.6,
                pos.getY() + 0.4 + random.nextDouble() * 0.5, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0.015, 0);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos p_60557_, CollisionContext p_60558_) {
        return ResourcePlantShapes.get(profile(), state.getValue(AGE), isExhausted(state));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3 && !isExhausted(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int BUSH_AGE = state.getValue(AGE);
        if (BUSH_AGE >= MAX_AGE || !canGrow(level, pos, state)) {
            return;
        }
        if (Config.REQUIRE_LIGHT.get() && level.getRawBrightness(pos.above(), 0) < Config.MIN_LIGHT.get()) {
            return;
        }
        if (random.nextInt(100) >= Config.GROWTH_CHANCE_PERCENT.get()) {
            return;
        }
        if (random.nextInt(profile().tier * 4) != 0) return;
        if (!net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, true)) return;
        level.setBlock(pos, state.setValue(AGE, BUSH_AGE + 1), 2);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier applier, boolean isPrecise) {
        if (entity instanceof LivingEntity){
            entity.makeStuckInBlock(state, new Vec3((double)0.8F, 0.75D, (double)0.8F));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HARVESTS);
    }

    //Abstract methods

    @Override
    public abstract ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state, boolean includeData);

    public abstract ItemStack getItem(int ItemKey);

    public abstract ItemStack getDropItem(int ItemKey, int amount);

    @Override
    public abstract com.mojang.serialization.MapCodec<net.minecraft.world.level.block.BushBlock> codec();

    // Bonemeal support
    @Override
    public boolean isValidBonemealTarget(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state) {
        return Config.ENABLE_BONEMEAL.get() && profile().tier == 1 && !isExhausted(state)
                && state.getValue(AGE) < MAX_AGE && level instanceof Level world && canGrow(world, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int currentAge = state.getValue(AGE);
        if (currentAge < MAX_AGE && profile().tier == 1 && canGrow(level, pos, state)) {
            level.setBlock(pos, state.setValue(AGE, currentAge + 1), 2);
        }
    }

}
