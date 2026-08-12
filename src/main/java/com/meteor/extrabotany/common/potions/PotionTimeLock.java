package com.meteor.extrabotany.common.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class PotionTimeLock extends MobEffect {

    public PotionTimeLock() {
        super(MobEffectCategory.HARMFUL, 0xFFD700);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplified) {
        if(living.getDeltaMovement().y < 0)
            living.setDeltaMovement(living.getDeltaMovement().scale(0.03D));
    }

}
