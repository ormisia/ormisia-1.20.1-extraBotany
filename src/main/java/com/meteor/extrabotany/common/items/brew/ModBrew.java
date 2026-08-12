package com.meteor.extrabotany.common.items.brew;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.api.brew.Brew;

import java.util.Arrays;
import java.util.List;

public class ModBrew {

    public static final DeferredRegister<Brew> BREWS = DeferredRegister.create(BotaniaRegistries.BREWS, LibMisc.MOD_ID);

    public static final RegistryObject<Brew> revolution = make("revolution", 10000, new MobEffectInstance(MobEffects.UNLUCK, 1800, 2),
            new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 2));
    public static final RegistryObject<Brew> shell = make("shell", 10000, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 2),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 2));
    public static final RegistryObject<Brew> allmighty = make("allmighty", 30000, new MobEffectInstance(MobEffects.ABSORPTION, 900, 0),
            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 0), new MobEffectInstance(MobEffects.DIG_SPEED, 900, 0),
            new MobEffectInstance(MobEffects.JUMP, 900, 0), new MobEffectInstance(MobEffects.LUCK, 900, 0),
            new MobEffectInstance(MobEffects.REGENERATION, 900, 0), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 0));
    public static final RegistryObject<Brew> deadpool = make("deadpool", 20000, new MobEffectInstance(MobEffects.WITHER, 300, 1),
            new MobEffectInstance(MobEffects.POISON, 300, 1), new MobEffectInstance(MobEffects.GLOWING, 3600, 2),
            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 2));
    public static final RegistryObject<Brew> floating = make("floating", 2000, new MobEffectInstance(MobEffects.LEVITATION, 160, 2));

    private static RegistryObject<Brew> make(String name, int cost, MobEffectInstance... effects) {
        List<MobEffectInstance> list = Arrays.asList(effects);
        return BREWS.register(name, () -> new Brew(PotionUtils.getColor(list), cost, effects));
    }

}
