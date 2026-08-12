package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.client.fx.WispParticleData;

import java.util.List;

public class EntityTrueTerrabladeProjectile extends EntityProjectileBase{

    public static final int LIVE_TICKS = 60;

    public EntityTrueTerrabladeProjectile(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityTrueTerrabladeProjectile(Level worldIn, LivingEntity thrower) {
        super(ModEntities.TRUETERRABLADE.get(), worldIn, thrower);
    }

    @Override
    public void tick() {

        if (this.tickCount >= LIVE_TICKS)
            this.discard();

        if (!level().isClientSide && (getThrower() == null || getThrower().isRemoved())) {
            this.discard();
            return;
        }

        super.tick();

        if(level().isClientSide && tickCount % 2 == 0){
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.95F, 0.1F ,1F);
            level().addParticle(data, getX(), getY(), getZ(), 0, 0, 0);
        }

        if (!level().isClientSide && tickCount % 3 == 0) {
            AABB axis = new AABB(getX(), getY(), getZ(), xOld, yOld, zOld).inflate(2);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(getThrower() instanceof Player) {
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 11F, DamageHandler.INSTANCE.NETURAL);
                }else{
                    if(living.invulnerableTime == 0)
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC);
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BakedModel getIcon(){
        return MiscellaneousIcons.INSTANCE.trueterrabladeprojectileModel[0];
    }

}
