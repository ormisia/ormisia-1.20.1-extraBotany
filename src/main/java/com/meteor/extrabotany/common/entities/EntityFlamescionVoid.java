package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityFlamescionVoid extends Entity {

    private Player owner;
    private float damage = 1.5F;

    public EntityFlamescionVoid(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityFlamescionVoid(Level worldIn, Player owner) {
        super(ModEntities.VOID.get(), worldIn);
        this.owner = owner;
    }

    @Override
    public void tick(){
        super.tick();
        dmg();
    }

    public void dmg(){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null) {
                if (entity instanceof Enemy) {
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 3));
                }
                if (entity == owner)
                    continue;
                Vec3 vec = this.position().subtract(entity.position());
                entity.setDeltaMovement(vec.normalize().scale(1.5D));
            }
        }

        if(this.tickCount % 15 == 0){
            damageAllAround(damage);
        }

        if(this.tickCount >= 40)
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
        float range = 6F;
        return level().getEntitiesOfClass(LivingEntity.class,
                new AABB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
