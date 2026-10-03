package me.ez.orebushes.Common.Bushes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

/** Two-box selection outlines for the v2 models, not per-cube collision meshes. */
public final class ResourcePlantShapes {
    private static final double[] GROWTH_SCALE = {0.30D, 0.52D, 0.78D, 1.0D, 0.65D};
    private static final int SPENT = 4;
    private static final Map<ResourcePlantProfile, VoxelShape[]> SHAPES = new EnumMap<>(ResourcePlantProfile.class);

    static {
        for (ResourcePlantProfile profile : ResourcePlantProfile.values()) {
            // Crown bounds are rounded outward from the mature Blockbench exports.
            // The shallow base covers the full 16x16 resource-growth footprint.
            double[] crown = switch (profile) {
                case COAL -> new double[]{2, 0.5, 14, 12.5, 13};
                case IRON -> new double[]{1, 1, 15, 15, 15};
                case GOLD -> new double[]{1.5, 1.5, 14.5, 14.5, 10.5};
                case EMERALD -> new double[]{2.5, 2.5, 14, 13.5, 15};
                case REDSTONE -> new double[]{2.5, 2.5, 13.5, 13.5, 15};
                case LAPIS -> new double[]{2.5, 1, 13, 13, 13.5};
                case DIAMOND -> new double[]{1.5, 2, 14.5, 14, 15};
                case COPPER -> new double[]{1.5, 2, 13, 11, 13.5};
                case AMETHYST -> new double[]{2, 2.5, 13.5, 13.5, 15};
                case EXPERIENCE -> new double[]{2, 1, 13, 14, 13};
                case ECHO_SHARD -> new double[]{2, 4, 14, 12, 15};
                case GOLDEN_APPLE -> new double[]{1, 1.5, 15, 13.5, 14.5};
                case SUGAR -> new double[]{1.5, 1, 13.5, 13, 13};
                case QUARTZ -> new double[]{1.5, 3, 14.5, 13, 15};
                case GLOWSTONE -> new double[]{2, 2, 14, 14, 15};
                case NETHERITE -> new double[]{2.5, 2.5, 13.5, 13.5, 15};
                case ANCIENT_DEBRIS -> new double[]{2.5, 0.5, 13.5, 12.5, 13.5};
                case BLAZE -> new double[]{2.5, 2.5, 13.5, 13.5, 16};
                case ENDER_PEARL -> new double[]{2, 1, 14, 14, 12};
                case ENDER_EYE -> new double[]{1.5, 2.5, 14.5, 13, 14.5};
                case CHORUS -> new double[]{1, 1, 14, 14, 14.5};
                case SHULKER_SHELL -> new double[]{3.5, 3.5, 12.5, 12.5, 13};
                case DRAGON_BREATH -> new double[]{3.5, 3.5, 12.5, 12.5, 15.5};
            };
            VoxelShape[] stages = new VoxelShape[GROWTH_SCALE.length];
            for (int stage = 0; stage < stages.length; stage++) {
                double scale = GROWTH_SCALE[stage];
                VoxelShape base = Block.box(center(0, scale), 0, center(0, scale),
                        center(16, scale), 3 * scale, center(16, scale));
                VoxelShape top = Block.box(center(crown[0], scale), 3 * scale, center(crown[1], scale),
                        center(crown[2], scale), crown[4] * scale, center(crown[3], scale));
                stages[stage] = Shapes.or(base, top).optimize();
            }
            SHAPES.put(profile, stages);
        }
    }

    private ResourcePlantShapes() {}

    private static double center(double coordinate, double scale) {
        return 8 + (coordinate - 8) * scale;
    }

    public static VoxelShape get(ResourcePlantProfile profile, int age, boolean exhausted) {
        return SHAPES.get(profile)[exhausted ? SPENT : age];
    }
}
