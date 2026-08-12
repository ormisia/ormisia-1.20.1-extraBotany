package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityFlamescionSlash extends Entity {

    private Player owner;
    private float damage = 1F;

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityFlamescionSlash.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(EntityFlamescionSlash.class,
            EntityDataSerializers.FLOAT);

    public EntityFlamescionSlash(EntityType<? extends EntityFlamescionSlash> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityFlamescionSlash(Level worldIn, Player owner) {
        super(ModEntities.FLAMESCIONSLASH.get(), worldIn);
        this.owner = owner;
        setRotation((float) (120F * Math.random()) - 60F);
        setPitch((float) (360F * Math.random()));
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ROTATION, 0F);
        entityData.define(PITCH, 0F);
    }

    @Override
    public void tick(){
        super.tick();
        dmg();
    }

    public void dmg(){
        for(LivingEntity entity : getEntitiesAround()){
            if(entity instanceof Enemy){
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 3));
            }
        }

        if(this.tickCount == 2 || this.tickCount == 5){
            damageAllAround(damage);
        }

        if(this.tickCount >= 6)
            this.discard();
    }

    public void damageAllAround(float dmg){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null){
                if(entity == owner)
                    continue;
                entity.invulnerableTime = 0;
                entity.hurt(FlamescionHandler.flameSource(entity), dmg);
            }
        }
    }

    public List<LivingEntity> getEntitiesAround() {
        BlockPos source = BlockPos.containing(getX(), getY(), getZ());
        float range = 3.5F;
        return level().getEntitiesOfClass(LivingEntity.class,
                new AABB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        setRotation(cmp.getFloat(TAG_ROTATION));
        setPitch(cmp.getFloat(TAG_PITCH));
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

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

}
