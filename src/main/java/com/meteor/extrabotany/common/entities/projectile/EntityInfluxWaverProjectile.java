package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.client.fx.WispParticleData;

import java.util.List;

public class EntityInfluxWaverProjectile extends EntityProjectileBase{

    public static final int LIVE_TICKS = 60;

    private static final String TAG_STRIKE_TIMES = "strike_times";
    private static final String TAG_NEXT = "next";
    private static final EntityDataAccessor<Integer> STRIKE_TIMES = SynchedEntityData.defineId(EntityInfluxWaverProjectile.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> NEXT = SynchedEntityData.defineId(EntityInfluxWaverProjectile.class,
            EntityDataSerializers.BLOCK_POS);

    private int removeFlag = -1;

    public EntityInfluxWaverProjectile(EntityType<? extends EntityProjectileBase> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityInfluxWaverProjectile(Level worldIn, LivingEntity thrower) {
        super(ModEntities.INFLUXWAVER.get(), worldIn, thrower);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STRIKE_TIMES, 0);
        entityData.define(NEXT, BlockPos.ZERO);
    }

    @Override
    public void tick() {

        if(this.removeFlag != -1 && this.tickCount >= this.removeFlag + 4){
            if(!level().isClientSide && !getNext().equals(BlockPos.ZERO)) {
                EntityInfluxWaverProjectile proj = make(getNext());
                level().addFreshEntity(proj);
                this.discard();
            }
        }

        if (this.tickCount >= LIVE_TICKS)
            this.discard();

        if (!level().isClientSide && (getThrower() == null || getThrower().isRemoved())) {
            this.discard();
            return;
        }

        super.tick();

        if(this.removeFlag != -1)
            return;

        if(level().isClientSide && tickCount % 2 == 0){
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.1F, 0.85F ,1F);
            level().addParticle(data, getX(), getY(), getZ(), 0, 0, 0);
        }

        if (!level().isClientSide) {
            AABB axis = new AABB(getX(), getY(), getZ(), xOld, yOld, zOld).inflate(2);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            boolean flag = false;
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(!living.isRemoved()) {
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                    if(getThrower() instanceof Player) {
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 12F, DamageHandler.INSTANCE.NETURAL);
                    }else{
                        if(living.invulnerableTime == 0)
                            DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC);
                    }
                    flag = living.isRemoved();
                    if(getStrikeTimes() > 0 && !flag){
                        setNext(living.blockPosition().offset(0, 1, 0));
                        removeFlag = this.tickCount;
                    }
                    break;
                }
            }

            if(getStrikeTimes() > 0 && flag){
                axis = axis.inflate(5D);
                List<LivingEntity> others = level().getEntitiesOfClass(LivingEntity.class, axis);
                List<LivingEntity> olist = DamageHandler.INSTANCE.getFilteredEntities(others, getThrower());
                for(LivingEntity living : olist){
                    setNext(living.blockPosition().offset(0, 1, 0));
                    removeFlag = this.tickCount;
                    break;
                }
            }
        }

    }

    public EntityInfluxWaverProjectile make(BlockPos targetpos){
        EntityInfluxWaverProjectile proj = new EntityInfluxWaverProjectile(level(), getThrower());
        float range = 6F;
        double j = -Math.PI + 2 * Math.PI * Math.random();
        double k;
        double x,y,z;
        k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
        x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
        y = targetpos.getY() + range * Math.cos(k);
        z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
        proj.moveTo(x,y,z);
        proj.setTargetPos(targetpos);
        proj.faceTarget(0.8F);
        proj.setStrikeTimes(getStrikeTimes()-1);
        return proj;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        cmp.putInt(TAG_STRIKE_TIMES, getStrikeTimes());
        cmp.putLong(TAG_NEXT, getNext().asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        setStrikeTimes(cmp.getInt(TAG_STRIKE_TIMES));
        setNext(BlockPos.of(cmp.getLong(TAG_NEXT)));
    }

    public int getStrikeTimes(){
        return entityData.get(STRIKE_TIMES);
    }

    public void setStrikeTimes(int i){
        entityData.set(STRIKE_TIMES, i);
    }

    public BlockPos getNext(){
        return entityData.get(NEXT);
    }

    public void setNext(BlockPos pos){
        entityData.set(NEXT, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BakedModel getIcon(){
        return MiscellaneousIcons.INSTANCE.influxwaverprojectileModel[0];
    }

}
