package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityKeyOfTruth extends Entity {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TARGET = "target";
    private static final String TAG_TYPE = "type";

    private static Player owner;
    private int countdown = 5;
    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(EntityKeyOfTruth.class,
            EntityDataSerializers.BOOLEAN);

    public EntityKeyOfTruth(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityKeyOfTruth(Level worldIn, Player owner) {
        super(ModEntities.KEY_OF_TRUTH.get(), worldIn);
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ROTATION, 0F);
        entityData.define(DAMAGE, 0F);
        entityData.define(PITCH, 0F);
        entityData.define(TARGET, -1);
        entityData.define(TYPE, 0);
        entityData.define(SHOOT,false);
    }

    @Override
    public void tick() {
        super.tick();

        if (owner != null) {

            if (getTarget() == -1) {
                if (owner.getLastHurtMob() != null && owner.getLastHurtMob().hasLineOfSight(owner)) {
                    setTarget(owner.getLastHurtMob().getId());
                } else for (LivingEntity living : getEntitiesAround()) {
                    if (living == owner)
                        continue;
                    if (living instanceof Enemy) {
                        setTarget(living.getId());
                        break;
                    }
                }
            }

            Entity target = level().getEntity(getTarget());

            if (target == null)
                this.discard();
            if (target != null) {
                this.faceEntity(target, 360F, 360F);

                setRotation(Mth.wrapDegrees(-this.getYRot() + 180));
                setPitch(-this.getXRot() + 360);

                if (tickCount % 10 == 0 && !getShoot()) {
                    level().playSound(null, getX(), getY(), getZ(), ModSounds.shoot.get(), SoundSource.PLAYERS, 0.25F, 1F);
                    setShoot(true);
                    this.countdown = 4;
                }

                if (this.countdown > 0) {
                    this.countdown--;
                    if (target != null) {
                        DamageSource dmgSrc = owner.damageSources().playerAttack(owner);
                        target.hurt(dmgSrc, 0.01F);
                        HerrscherHandler.iceAttack(target, owner, 4F);
                    }
                }

                if (this.countdown == 0)
                    setShoot(false);
            }

            if (tickCount >= 45)
                this.discard();
        }
    }

    public List<LivingEntity> getEntitiesAround() {
        BlockPos source = BlockPos.containing(getX(), getY(), getZ());
        float range = 12F;
        return level().getEntitiesOfClass(LivingEntity.class,
                new AABB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
        double d0 = entityIn.getX() - this.getX();
        double d2 = entityIn.getZ() - this.getZ();
        double d1;
        if (entityIn instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entityIn;
            d1 = livingentity.getEyeY() - this.getEyeY();
        } else {
            d1 = (entityIn.getBoundingBox().minY + entityIn.getBoundingBox().maxY) / 2.0D - this.getEyeY();
        }

        double d3 = (double) Mth.sqrt((float) (d0 * d0 + d2 * d2));
        float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
        float f1 = (float) (-(Mth.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
        this.setXRot(this.updateRotation(this.getXRot(), f1, maxPitchIncrease));
        this.setYRot(this.updateRotation(this.getYRot(), f, maxYawIncrease));
    }

    private float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float f = Mth.wrapDegrees(targetAngle - angle);
        if (f > maxIncrease) {
            f = maxIncrease;
        }

        if (f < -maxIncrease) {
            f = -maxIncrease;
        }

        return angle + f;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        setRotation(cmp.getFloat(TAG_ROTATION));
        setDamage(cmp.getFloat(TAG_DAMAGE));
        setPitch(cmp.getFloat(TAG_PITCH));
        setTarget(cmp.getInt(TAG_TARGET));
        setKeyType(cmp.getInt(TAG_TYPE));
        setShoot(cmp.getBoolean("shoot"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_DAMAGE, getDamage());
        cmp.putFloat(TAG_PITCH, getPitch());
        cmp.putInt(TAG_TARGET, getTarget());
        cmp.putInt(TAG_TYPE, getKeyType());
        cmp.putBoolean("shoot", getShoot());
    }

    public boolean getShoot() {
        return entityData.get(SHOOT);
    }

    public void setShoot(boolean shoot) {
        entityData.set(SHOOT,shoot);
    }

    public int getKeyType() {
        return entityData.get(TYPE);
    }

    public void setKeyType(int rot) {
        entityData.set(TYPE, rot);
    }

    public int getTarget() {
        return entityData.get(TARGET);
    }

    public void setTarget(int rot) {
        entityData.set(TARGET, rot);
    }

    public float getRotation() {
        return entityData.get(ROTATION);
    }

    public void setRotation(float rot) {
        entityData.set(ROTATION, rot);
    }

    public float getPitch() {
        return entityData.get(PITCH);
    }

    public void setPitch(float rot) {
        entityData.set(PITCH, rot);
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float delay) {
        entityData.set(DAMAGE, delay);
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

}
