package com.meteor.extrabotany.client.particles;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LibMisc.MOD_ID);

    /**
     * TODO(1.20.1): no particles were registered in the 1.16 source. If custom particles are ever added,
     * register them here as DeferredRegister entries and bind ParticleProviders on ParticleFactoryRegisterEvent.
     */
    public static void registerParticles() {
    }

    public static class FactoryHandler {
        public static void registerFactories() {
        }
    }

}
