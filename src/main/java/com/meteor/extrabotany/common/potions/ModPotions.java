package com.meteor.extrabotany.common.potions;

import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.common.libs.LibPotionNames;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<MobEffect> MODS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LibMisc.MOD_ID);

    public static final RegistryObject<MobEffect> incandescence = MODS.register(LibPotionNames.INCANDESCENCE, PotionIncandescence::new);
    public static final RegistryObject<MobEffect> timelock = MODS.register(LibPotionNames.TIMELOCK, PotionTimeLock::new);
    public static final RegistryObject<MobEffect> flamescion = MODS.register(LibPotionNames.FLAMESCION, PotionFlamescion::new);
    public static final RegistryObject<MobEffect> bloodtemptation = MODS.register(LibPotionNames.BLOODTEMPTATION, PotionBloodTempation::new);
}
