package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class EntityPhantomSword extends EntityProjectileBase {

    public static final int LIVE_TICKS = 30;

    private static final String TAG_VARIETY = "variety";
    private static final String TAG_DELAY = "delay";
    private static final String TAG_FAKE = "fake";

    private static final EntityDataAccessor<Integer> VARIETY = SynchedEntityData.defineId(EntityPhantomSword.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DELAY = SynchedEntityData.defineId(EntityPhantomSword.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FAKE = SynchedEntityData.defineId(EntityPhantomSword.class,
            EntityDataSerializers.BOOLEAN);

    private static final float rgb[][] = { { 0.82F, 0.2F, 0.58F }, { 0F, 0.71F, 0.10F }, { 0.74F, 0.07F, 0.32F },
            { 0.01F, 0.45F, 0.8F }, { 0.05F, 0.39F, 0.9F }, { 0.38F, 0.34F, 0.42F }, { 0.41F, 0.31F, 0.14F },
            { 0.92F, 0.92F, 0.21F }, { 0.61F, 0.92F, 0.98F }, { 0.18F, 0.45F, 0.43F } };

    public EntityPhantomSword(EntityType<EntityPhantomSword> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityPhantomSword(Level worldIn) {
        super(ModEntities.PHANTOMSWORD.get(), worldIn);
    }

    public EntityPhantomSword(Level world, LivingEntity thrower, BlockPos targetpos) {
        super(ModEntities.PHANTOMSWORD.get(), world, thrower);
        setTargetPos(targetpos);
        setVariety((int) (10 * Math.random()));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIETY, 0);
        entityData.define(DELAY, 0);
        entityData.define(FAKE, false);
    }

    @Override
    public void tick() {

        if (getDelay() > 0) {
            setDelay(getDelay() - 1);
            return;
        }

        if (this.tickCount >= LIVE_TICKS)
            this.discard();

        if(getFake()) {
            this.setDeltaMovement(0D,0D,0D);
            return;
        }

        if (!getFake() && !level().isClientSide && (getThrower() == null || getThrower().isRemoved())) {
            this.discard();
            return;
        }

        super.tick();

        if(!level().isClientSide && !getFake() && this.tickCount % 6 == 0) {
            EntityPhantomSword illusion = new EntityPhantomSword(level());
            illusion.setFake(true);
            illusion.setRotation(this.getRotation());
            illusion.setPitch(this.getPitch());
            illusion.moveTo(getX(), getY(), getZ());
            illusion.setVariety(getVariety());
            level().addFreshEntity(illusion);
        }

        if (!level().isClientSide) {
            AABB axis = new AABB(getX(), getY(), getZ(), xOld, yOld, zOld).inflate(2);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            List<LivingEntity> list = DamageHandler.INSTANCE.getFilteredEntities(entities, getThrower());
            for (LivingEntity living : list) {
                if(getThrower() instanceof Player) {
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7F, DamageHandler.INSTANCE.MAGIC_PIERCING);
                }else{
                    if(living.invulnerableTime == 0)
                        DamageHandler.INSTANCE.dmg(living, getThrower(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    DamageHandler.INSTANCE.dmg(living, getThrower(), 7.5F, DamageHandler.INSTANCE.MAGIC);
                }
            }
        }

    }

    @Override
    protected void onHit(HitResult result) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        cmp.putInt(TAG_VARIETY, getVariety());
        cmp.putInt(TAG_DELAY, getDelay());
        cmp.putBoolean(TAG_FAKE, getFake());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        setVariety(cmp.getInt(TAG_VARIETY));
        setDelay(cmp.getInt(TAG_DELAY));
        setFake(cmp.getBoolean(TAG_FAKE));
    }

    public int getVariety() {
        return entityData.get(VARIETY);
    }

    public void setVariety(int var) {
        entityData.set(VARIETY, var);
    }

    public int getDelay() {
        return entityData.get(DELAY);
    }

    public void setDelay(int var) {
        entityData.set(DELAY, var);
    }

    public boolean getFake() {
        return entityData.get(FAKE);
    }

    public void setFake(boolean rot) {
        entityData.set(FAKE, rot);
    }

}
