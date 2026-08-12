package com.meteor.extrabotany.common.entities.mountable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public abstract class EntityMountable extends net.minecraft.world.entity.vehicle.Boat {

    private static final String TAG_PITCH = "pitch";
    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_MOUNTABLE = "mountable";
    private static final String TAG_DRIVERID = "driverid";

    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(EntityMountable.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityMountable.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> MOUNTABLE = SynchedEntityData.defineId(EntityMountable.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DRIVERID = SynchedEntityData.defineId(EntityMountable.class,
            EntityDataSerializers.INT);

    public boolean ctrlInputDown = false;
    public boolean spaceInputDown = false;
    public boolean forwardInputDown = false;
    public boolean backInputDown = false;
    public boolean leftInputDown = false;
    public boolean rightInputDown = false;

    public EntityMountable(EntityType<? extends Boat> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    // In 1.20.1 the Boat input fields are private and only writable through setInput(),
    // so we capture them here for the subclasses (previously public fields in 1.16).
    @Override
    public void setInput(boolean inputLeft, boolean inputRight, boolean inputUp, boolean inputDown) {
        super.setInput(inputLeft, inputRight, inputUp, inputDown);
        this.leftInputDown = inputLeft;
        this.rightInputDown = inputRight;
        this.forwardInputDown = inputUp;
        this.backInputDown = inputDown;
    }

    @Override
    public void tick() {
        if(tickCount <= 3)
            return;
        if(getMountable()){
            if(this.getPassengers().isEmpty() || !this.getPassengers().isEmpty() && !(this.getPassengers().get(0) instanceof Player)){
                this.discard();
                return;
            }
        }
        super.tick();
    }

    @Override
    protected SoundEvent getPaddleSound() {
        return null;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROTATION, 0F);
        this.entityData.define(PITCH, 0F);
        this.entityData.define(MOUNTABLE,false);
    }

    @Override
    public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source) {
        // 1.16 had isImmuneToExplosions() returning true; mapped to explosion immunity here.
        return super.isInvulnerableTo(source) || source.is(net.minecraft.world.damagesource.DamageTypes.EXPLOSION);
    }

    public Item getItemBoat(){
        return null;
    }

    public ItemStack getItemStack(){
        return getMountable() ? ItemStack.EMPTY : new ItemStack(getItemBoat());
    }

    public void updateInput(boolean ctrlInputDown, boolean spaceInputDown) {
        this.ctrlInputDown = ctrlInputDown;
        this.spaceInputDown = spaceInputDown;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setRotation(compound.getFloat(TAG_ROTATION));
        setPitch(compound.getFloat(TAG_PITCH));
        setMountable(compound.getBoolean(TAG_MOUNTABLE));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat(TAG_ROTATION, getRotation());
        compound.putFloat(TAG_PITCH, getPitch());
        compound.putBoolean(TAG_MOUNTABLE, getMountable());
    }

    public void setMountable(boolean b){
        entityData.set(MOUNTABLE, b);
    }

    public boolean getMountable(){
        return entityData.get(MOUNTABLE);
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

    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(2.0D);
    }

}
