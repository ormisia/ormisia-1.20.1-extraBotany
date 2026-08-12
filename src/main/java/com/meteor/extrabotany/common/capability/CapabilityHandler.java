package com.meteor.extrabotany.common.capability;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CapabilityHandler {

    // 1.20.1 removed @CapabilityInject; capabilities are now obtained via CapabilityManager.get(new CapabilityToken<>(){}).
    // These static fields are initialized lazily and share the same instances that get registered by RegisterCapabilitiesEvent.
    public static final Capability<IHerrscherEnergy> HERRSCHERENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<IFlamescion> FLAMESCION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    /**
     * Kept for compatibility with the old 1.16 entry point called from common setup.
     * In 1.20.1 capabilities are registered through {@link RegisterCapabilitiesEvent} on the mod event bus,
     * handled by {@link RegisterHandler}.
     */
    public static void register() {
    }

    @Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegisterHandler {
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(IHerrscherEnergy.class);
            event.register(IFlamescion.class);
        }
    }

}
