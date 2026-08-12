package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.BrewItem;

import javax.annotation.Nonnull;
import java.util.List;

public class EntitySplashGrenade extends ThrowableProjectile implements ItemSupplier{

    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData
            .defineId(EntitySplashGrenade.class, EntityDataSerializers.ITEM_STACK);

    private Player thrower;

    public EntitySplashGrenade(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntitySplashGrenade(Level worldIn, Player thrower) {
        super(ModEntities.SPLASHGRENADE.get(), worldIn);
        this.thrower = thrower;
    }

    @Override
    public void tick() {

        if (!level().isClientSide && (thrower == null || thrower.isRemoved())) {
            this.discard();
            return;
        }

        if (!level().isClientSide) {
            AABB axis = new AABB(getX() - 0.2F, getY() - 0.2F, getZ() - 0.2F, xOld + 0.2F,
                    yOld + 0.2F, zOld + 0.2F);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            for (LivingEntity living : entities) {
                if(living == thrower)
                    continue;
                doImpact();
                break;
            }
        }

        super.tick();
    }

    public void doImpact() {
        if (getPotion().getItem() instanceof BrewItem bi) {
            Brew brew = bi.getBrew(getPotion());
            double range = 5;
            AABB bounds = new AABB(getX() - range, getY() - range, getZ() - range, getX() + range,
                    getY() + range, getZ() + range);
            List<LivingEntity> entitiess;
            entitiess = level().getEntitiesOfClass(LivingEntity.class, bounds);
            for (LivingEntity living2 : entitiess) {
                if (!(living2 instanceof Player))
                    living2.hurt(living2.damageSources().magic(), 10F);
                for (MobEffectInstance effect : brew.getPotionEffects(getPotion())) {
                    MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(),
                            (int) ((float) effect.getDuration() * 0.6F), effect.getAmplifier(), true, true);
                    if (!(living2 instanceof Player) && !effect.getEffect().isBeneficial()) {
                        if (effect.getEffect().isInstantenous())
                            effect.getEffect().applyInstantenousEffect(living2, living2, living2, newEffect.getAmplifier(), 1.0D);
                        else
                            living2.addEffect(newEffect);
                    } else if (living2 instanceof Player && effect.getEffect().isBeneficial()) {
                        if (effect.getEffect().isInstantenous())
                            effect.getEffect().applyInstantenousEffect(living2, living2, living2, newEffect.getAmplifier(), 1.0D);
                        else
                            living2.addEffect(newEffect);
                    }
                    int i = effect.getEffect().isInstantenous() ? 2007 : 2002;
                    this.level().levelEvent(i, this.blockPosition(), brew.getColor(getPotion()));
                }
            }
        }
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().thrown(this, this.thrower), 5.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            doImpact();
        }
    }

    @Override
    protected float getGravity() {
        return 0.02F;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ITEM, ItemStack.EMPTY);
    }

    public void setItem(ItemStack stack) {
        this.entityData.set(ITEM, Util.make(stack.copy(), (p_213883_0_) -> {
            p_213883_0_.setCount(1);
        }));
    }

    public ItemStack getPotion() {
        ItemStack itemstack = this.entityData.get(ITEM);
        return itemstack;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        ItemStack itemstack = this.getPotion();
        if (!itemstack.isEmpty()) {
            cmp.put("Potion", itemstack.save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        ItemStack itemstack = ItemStack.of(cmp.getCompound("Potion"));
        this.setItem(itemstack);
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return getPotion();
    }
}
