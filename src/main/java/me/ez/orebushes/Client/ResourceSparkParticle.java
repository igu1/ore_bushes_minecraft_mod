package me.ez.orebushes.Client;

import me.ez.orebushes.Main;
import me.ez.orebushes.PlantEffects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/** Client-only sprite registration: dedicated servers never load renderer classes. */
@EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ResourceSparkParticle extends SingleQuadParticle {
    private ResourceSparkParticle(ClientLevel level, double x, double y, double z,
                                  double vx, double vy, double vz, TextureAtlasSprite sprite) {
        super(level, x, y, z, vx, vy, vz, sprite);
        xd = vx;
        yd = vy + 0.015;
        zd = vz;
        lifetime = 18 + random.nextInt(14);
        quadSize = 0.06F + random.nextFloat() * 0.035F;
        friction = 0.92F;
        hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        alpha = Math.max(0, 1.0F - (float) age / lifetime);
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @SubscribeEvent
    public static void register(RegisterParticleProvidersEvent event) {
        PlantEffects.SPARKS.values().forEach(type -> event.registerSpriteSet(type.get(),
                (net.minecraft.client.particle.ParticleResources.SpriteParticleRegistration<SimpleParticleType>)
                        sprites -> new Provider(sprites)));
    }

    /** Sprite-based provider for the spark particles. */
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public ResourceSparkParticle createParticle(SimpleParticleType options, ClientLevel level,
                                                    double x, double y, double z,
                                                    double vx, double vy, double vz, RandomSource random) {
            TextureAtlasSprite sprite = this.sprites.get(random);
            return new ResourceSparkParticle(level, x, y, z, vx, vy, vz, sprite);
        }
    }
}
