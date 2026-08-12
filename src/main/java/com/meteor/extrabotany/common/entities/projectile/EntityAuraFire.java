package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class EntityAuraFire extends EntityProjectileBase{

    public EntityAuraFire(EntityType<? extends EntityProjectileBase> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityAuraFire(Level worldIn, LivingEntity thrower) {
        super(ModEntities.AURAFIRE.get(), worldIn, thrower);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 80)
            this.discard();
        if (this.level().isClientSide)
            for (int i = 0; i < 5; i++)
                this.level().addParticle(ParticleTypes.FLAME, this.getX() + Math.random() * 0.4F - 0.2F,
                        this.getY() + Math.random() * 0.4F - 0.2F, this.getZ() + Math.random() * 0.4F - 0.2F, 0, 0, 0);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if(getThrower() instanceof Player player){
            if(result.getEntity() != player){
                float dmg = ExtraBotanyAPI.calcDamage(5F, player);
                DamageHandler.INSTANCE.dmg(result.getEntity(), player, dmg, DamageHandler.INSTANCE.NETURAL_PIERCING);
                player.setAbsorptionAmount(Math.min(10, player.getAbsorptionAmount()+1F));
                this.discard();
            }
        }
    }

}
