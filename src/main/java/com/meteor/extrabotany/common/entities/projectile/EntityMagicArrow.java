package com.meteor.extrabotany.common.entities.projectile;

import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import vazkii.botania.client.fx.WispParticleData;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityMagicArrow extends ThrowableProjectile {

    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_LIFE = "life";

    private static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(EntityMagicArrow.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(EntityMagicArrow.class,
            EntityDataSerializers.INT);

    private LivingEntity thrower;

    public EntityMagicArrow(EntityType<? extends EntityMagicArrow> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityMagicArrow(Level worldIn, LivingEntity thrower) {
        this(ModEntities.MAGICARROW.get(), worldIn);
        this.thrower = thrower;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DAMAGE, 0);
        entityData.define(LIFE, 0);
    }

    @Override
    public void tick(){
        super.tick();

        if (!level().isClientSide && (thrower == null || !(thrower instanceof Player) || thrower.isRemoved())) {
            this.discard();
            return;
        }

        if(level().isClientSide){
            WispParticleData data = WispParticleData.wisp(0.5F, 0.1F, 0.85F, 0.1F ,1F);
            level().addParticle(data, getX(), getY(), getZ(), 0, 0, 0);
        }

        Player player = (Player) thrower;
        if (!level().isClientSide) {
            AABB axis = new AABB(getX() - 2F, getY() - 2F, getZ() - 2F, xOld + 2F,
                    yOld + 2F, zOld + 2F);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            List<LivingEntity> livings = DamageHandler.INSTANCE.getFilteredEntities(entities, player);
            for (LivingEntity living : livings) {
                DamageHandler.INSTANCE.dmg(living, player, getDamage(), DamageHandler.INSTANCE.NETURAL_PIERCING);
            }
        }

        if (tickCount > getLife())
            this.discard();
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        cmp.putInt(TAG_LIFE, getLife());
        cmp.putInt(TAG_DAMAGE, getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        setLife(cmp.getInt(TAG_LIFE));
        setDamage(cmp.getInt(TAG_DAMAGE));
    }

    public int getLife() {
        return entityData.get(LIFE);
    }

    public void setLife(int delay) {
        entityData.set(LIFE, delay);
    }

    public int getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(int delay) {
        entityData.set(DAMAGE, delay);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

}
