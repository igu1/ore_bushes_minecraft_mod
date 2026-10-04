package me.ez.orebushes;

import me.ez.orebushes.Common.Bushes.ResourcePlantProfile;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.util.HashMap;
import java.util.Map;

public final class PlantEffects {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Main.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, Main.MOD_ID);
    public static final Map<String, DeferredHolder<SoundEvent, SoundEvent>> HARVEST_SOUNDS = new HashMap<>();
    public static final Map<String, DeferredHolder<ParticleType<?>, SimpleParticleType>> SPARKS = new HashMap<>();

    static {
        for (ResourcePlantProfile profile : ResourcePlantProfile.values()) {
            String sound = profile.id + "_harvest";
            HARVEST_SOUNDS.put(profile.id, SOUNDS.register(sound,
                    () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, sound))));
            SPARKS.put(profile.id, PARTICLES.register(profile.id + "_spark", () -> new SimpleParticleType(false)));
        }
    }

    private PlantEffects() {}
}
