package com.meteor.extrabotany.common.core;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LibMisc.MOD_ID);

    public static final RegistryObject<SoundEvent> cyclone = makeSoundEvent("cyclone");
    public static final RegistryObject<SoundEvent> rideon = makeSoundEvent("rideon");
    public static final RegistryObject<SoundEvent> shoot = makeSoundEvent("shoot");
    public static final RegistryObject<SoundEvent> slash = makeSoundEvent("slash");
    public static final RegistryObject<SoundEvent> flamescionult = makeSoundEvent("flamescionult");

    public static final RegistryObject<SoundEvent> swordland = makeSoundEvent("music.ego");
    public static final RegistryObject<SoundEvent> salvation = makeSoundEvent("music.herrscher");

    private static RegistryObject<SoundEvent> makeSoundEvent(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, name)));
    }

    private ModSounds() {}
}
