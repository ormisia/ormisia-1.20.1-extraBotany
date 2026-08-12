package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class EntityButterflyProjectile extends EntityProjectileBase{

    public EntityButterflyProjectile(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityButterflyProjectile(Level worldIn, LivingEntity thrower) {
        super(ModEntities.BUTTERFLY.get(), worldIn, thrower);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > 100)
            this.discard();
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (getThrower() instanceof Player) {
            Player player = (Player) getThrower();
            if (result.getEntity() != player) {

            }
        }
    }

}
