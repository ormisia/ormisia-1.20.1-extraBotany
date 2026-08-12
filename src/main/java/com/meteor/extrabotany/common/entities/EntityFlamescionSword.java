package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.handler.FlamescionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityFlamescionSword extends ThrowableProjectile {

    private Player owner;
    private float damage = 4F;

    public EntityFlamescionSword(EntityType<? extends EntityFlamescionSword> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityFlamescionSword(Level worldIn, Player owner) {
        super(ModEntities.SWORD.get(), worldIn);
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        super.tick();

        if(tickCount % 4 == 0)
            damageAllAround(damage);

        if(this.tickCount >= 30)
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

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }


}
