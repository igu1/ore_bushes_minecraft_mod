package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.ResourcePlantProfile;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.HashMap;
import java.util.Map;

public final class PlantEffects {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MOD_ID);
    public static final Map<String, RegistryObject<SoundEvent>> HARVEST_SOUNDS = new HashMap<>();
    public static final Map<String, RegistryObject<SimpleParticleType>> SPARKS = new HashMap<>();

    static {
        for (ResourcePlantProfile profile : ResourcePlantProfile.values()) {
            String sound = profile.id + "_harvest";
            HARVEST_SOUNDS.put(profile.id, SOUNDS.register(sound,
                    () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, sound))));
            SPARKS.put(profile.id, PARTICLES.register(profile.id + "_spark", () -> new SimpleParticleType(false)));
        }
    }

    private PlantEffects() {}
}
