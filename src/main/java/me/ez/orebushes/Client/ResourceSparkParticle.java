package me.ez.orebushes.Client;

import me.ez.orebushes.Main;
import me.ez.orebushes.PlantEffects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

/** Client-only sprite registration: dedicated servers never load renderer classes. */
@EventBusSubscriber(modid = Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ResourceSparkParticle extends TextureSheetParticle {
    private ResourceSparkParticle(ClientLevel level, double x, double y, double z,
                                  double vx, double vy, double vz, SpriteSet sprites) {
        super(level, x, y, z, vx, vy, vz);
        xd = vx;
        yd = vy + 0.015;
        zd = vz;
        lifetime = 18 + random.nextInt(14);
        quadSize = 0.06F + random.nextFloat() * 0.035F;
        friction = 0.92F;
        hasPhysics = false;
        pickSprite(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        alpha = Math.max(0, 1.0F - (float) age / lifetime);
    }

    @Override
    public ParticleRenderType getRenderType() { return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; }

    @Override
    public int getLightColor(float partialTick) { return 0xF000F0; }

    @SubscribeEvent
    public static void register(RegisterParticleProvidersEvent event) {
        PlantEffects.SPARKS.values().forEach(type -> event.registerSpriteSet(type.get(),
                (net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration<SimpleParticleType>)
                        sprites -> (ParticleProvider<SimpleParticleType>) (particle, level, x, y, z, vx, vy, vz) ->
                                new ResourceSparkParticle(level, x, y, z, vx, vy, vz, sprites)));
    }
}
