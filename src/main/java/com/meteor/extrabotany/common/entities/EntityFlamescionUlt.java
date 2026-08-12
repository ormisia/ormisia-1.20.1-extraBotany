package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class EntityFlamescionUlt extends Entity {

    private Player owner;
    private float damage = 12F;

    public EntityFlamescionUlt(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityFlamescionUlt(Level worldIn, Player owner) {
        super(ModEntities.ULT.get(), worldIn);
        this.owner = owner;
    }

    @Override
    public void tick(){
        super.tick();
        if(this.tickCount == 1 && !level().isClientSide)
            this.playSound(ModSounds.flamescionult.get(), 1F, 1F);

        if(this.tickCount == 10 || this.tickCount == 35 || this.tickCount == 60){
            damageAllAround(damage);
        }

        if(this.tickCount >= 40) {
            if(level().isClientSide)
                level().addParticle(ParticleTypes.EXPLOSION,
                        getX() - 2D + Math.random() * 4D,
                        getY() - 2D + Math.random() * 4D,
                        getZ() - 2D + Math.random() * 4D,
                        0, 0, 0);
        }

        if(this.tickCount >= 85)
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
        float range = 8F;
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
