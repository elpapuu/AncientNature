package net.reaper.ancientnature.core.init;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AncientNature.MOD_ID);


    public static final RegistryObject<SimpleParticleType> REVIVAL_STAND_PARTICLE = register("revival_stand_particle", false);



    public static RegistryObject<SimpleParticleType> register(String name, boolean pOverrideLimiter){
        RegistryObject<SimpleParticleType> ret = PARTICLES.register(name, () -> new SimpleParticleType(pOverrideLimiter));
        return ret;
    }
}
