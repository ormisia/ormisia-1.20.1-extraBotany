package com.meteor.extrabotany.common.entities.projectile;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityProjectileBase extends ThrowableProjectile {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TARGETPOS = "targetpos";
    private static final String TAG_TARGETPOSX = "targetposx";
    private static final String TAG_TARGETPOSY = "targetposy";
    private static final String TAG_TARGETPOSZ = "targetposz";

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<BlockPos> TARGET_POS = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Float> TARGET_POS_X = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Y = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TARGET_POS_Z = SynchedEntityData.defineId(EntityProjectileBase.class,
            EntityDataSerializers.FLOAT);

    private LivingEntity thrower;

    public EntityProjectileBase(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityProjectileBase(EntityType<? extends ThrowableProjectile> type, Level worldIn, LivingEntity thrower) {
        super(type, worldIn);
        this.thrower = thrower;
        this.setNoGravity(true);
    }

    @Nullable
    public LivingEntity getThrower(){
        return this.thrower;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ROTATION, 0F);
        entityData.define(PITCH, 0F);
        entityData.define(TARGET_POS, BlockPos.ZERO);
        entityData.define(TARGET_POS_X, 0F);
        entityData.define(TARGET_POS_Y, 0F);
        entityData.define(TARGET_POS_Z, 0F);
    }

    public void faceTargetAccurately(float modifier){
        this.faceEntity(this.getTargetPosX(), this.getTargetPosY(), this.getTargetPosZ());
        Vec3 vec = new Vec3(getTargetPosX() - getX(), getTargetPosY() - getY(), getTargetPosZ() - getZ())
                .normalize();
        this.setDeltaMovement(vec.x * modifier, vec.y * modifier, vec.z * modifier);
    }

    public void faceTarget(float modifier){
        this.faceEntity(this.getTargetPos());
        Vec3 vec = new Vec3(getTargetPos().getX() - getX(), getTargetPos().getY() - getY(), getTargetPos().getZ() - getZ())
                .normalize();
        this.setDeltaMovement(vec.x * modifier, vec.y * modifier, vec.z * modifier);
    }

    public void setTargetPos(Vec3 vec) {
        setTargetPosX((float) vec.x);
        setTargetPosY((float) vec.y);
        setTargetPosZ((float) vec.z);
        setTargetPos(BlockPos.containing(vec));
    }

    public void faceEntity(float vx, float vy, float vz) {
        double d0 = vx - this.getX();
        double d2 = vz - this.getZ();
        double d1 = vy - this.getY();

        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        float f = (float) (Mth.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float) (-(Mth.atan2(d1, d3) * (180D / Math.PI)));
        this.setXRot(this.updateRotation(this.getXRot(), f1, 360F));
        this.setYRot(this.updateRotation(this.getYRot(), f, 360F));

        setPitch(-this.getXRot());
        setRotation(Mth.wrapDegrees(-this.getYRot() + 180));
    }

    public void faceEntity(BlockPos target) {
        double d0 = target.getX() - this.getX();
        double d2 = target.getZ() - this.getZ();
        double d1 = target.getY() - this.getY();

        double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
        float f = (float) (Mth.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float) (-(Mth.atan2(d1, d3) * (180D / Math.PI)));
        this.setXRot(this.updateRotation(this.getXRot(), f1, 360F));
        this.setYRot(this.updateRotation(this.getYRot(), f, 360F));

        setPitch(-this.getXRot());
        setRotation(Mth.wrapDegrees(-this.getYRot() + 180));
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
    public void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
        cmp.putLong(TAG_TARGETPOS, getTargetPos().asLong());
        cmp.putFloat(TAG_TARGETPOSX, getTargetPosX());
        cmp.putFloat(TAG_TARGETPOSY, getTargetPosY());
        cmp.putFloat(TAG_TARGETPOSZ, getTargetPosZ());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        setRotation(cmp.getFloat(TAG_ROTATION));
        setPitch(cmp.getFloat(TAG_PITCH));
        setTargetPos(BlockPos.of(cmp.getLong(TAG_TARGETPOS)));
        setTargetPosX(cmp.getFloat(TAG_TARGETPOSX));
        setTargetPosY(cmp.getFloat(TAG_TARGETPOSY));
        setTargetPosZ(cmp.getFloat(TAG_TARGETPOSZ));
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

    public BlockPos getTargetPos() {
        return entityData.get(TARGET_POS);
    }

    public void setTargetPos(BlockPos pos) {
        entityData.set(TARGET_POS, pos);
    }

    public float getTargetPosX() {
        return entityData.get(TARGET_POS_X);
    }

    public void setTargetPosX(float f) {
        entityData.set(TARGET_POS_X, f);
    }

    public float getTargetPosY() {
        return entityData.get(TARGET_POS_Y);
    }

    public void setTargetPosY(float f) {
        entityData.set(TARGET_POS_Y, f);
    }

    public float getTargetPosZ() {
        return entityData.get(TARGET_POS_Z);
    }

    public void setTargetPosZ(float f) {
        entityData.set(TARGET_POS_Z, f);
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public BakedModel getIcon(){
        return null;
    }

}
