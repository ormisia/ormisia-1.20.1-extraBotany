package com.meteor.extrabotany.common.items.lens;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.common.item.lens.Lens;

import java.util.List;

public class LensTrace extends Lens {

    private static final String TAG_HOME_ID = "homeID";

    @Override
    public void updateBurst(ManaBurst burst, ItemStack stack) {
        ThrowableProjectile entity = burst.entity();
        if(entity.level().isClientSide)
            return;
        AABB axis = new AABB(entity.getX(), entity.getY(), entity.getZ(), entity.xOld,
                entity.yOld, entity.zOld).inflate(4);
        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, axis);
        int homeID = entity.getPersistentData().getInt(TAG_HOME_ID);
        if(homeID == 0) {
            for (LivingEntity living : entities) {
                entity.getPersistentData().putInt(TAG_HOME_ID, living.getId());
                break;
            }
        }else {
            Entity result = entity.level().getEntity(homeID);
            if (result != null && result.distanceTo(entity) < 3F && !burst.isFake()) {
                Vec3 vecEntity = result.getBoundingBox().getCenter();
                Vec3 vecThis = entity.getBoundingBox().getCenter();
                Vec3 vecMotion = vecEntity.subtract(vecThis);
                Vec3 vecCurrentMotion = entity.getDeltaMovement();
                vecMotion = vecMotion.normalize().scale(vecCurrentMotion.length());
                entity.setDeltaMovement(vecMotion);
            }
        }
    }

}
