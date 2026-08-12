package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
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

import javax.annotation.Nonnull;
import java.util.List;

public class EntitySlash extends Entity {

    private Player owner;
    private float damage = 3F;

    public EntitySlash(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntitySlash(Level worldIn, Player owner) {
        super(ModEntities.SLASH.get(), worldIn);
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        super.tick();

        if(this.tickCount == 1){
            this.playSound(ModSounds.slash.get(), 1.2F, 1.0F);
        }

        for(LivingEntity entity : getEntitiesAround()){
            if(entity instanceof Enemy){
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 3));
            }
        }

        if(this.tickCount == 6 || this.tickCount == 15 || this.tickCount == 23){
            damageAllAround(damage);
        }

        if(this.tickCount == 27 || this.tickCount == 34){
            damageAllAround(damage * 2);
        }

        if(this.tickCount >= 35)
            this.discard();
    }

    public void damageAllAround(float dmg){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null){
                if(entity == owner)
                    continue;
                HerrscherHandler.thunderAttack(entity, owner, dmg);
                entity.invulnerableTime = 0;
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
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
