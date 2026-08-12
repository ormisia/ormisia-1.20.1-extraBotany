package com.meteor.extrabotany.common.potions;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class PotionIncandescence extends MobEffect {

    public PotionIncandescence() {
        super(MobEffectCategory.BENEFICIAL, 0xDC143C);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplified) {
        if(living.getDeltaMovement().y < 0)
            living.setDeltaMovement(living.getDeltaMovement().multiply(1D, 0.05D, 1D));
        if(living instanceof Player player && !FlamescionHandler.isFlamescionMode(player))
            living.removeEffect(this);
    }

}
