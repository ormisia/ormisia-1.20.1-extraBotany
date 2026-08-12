package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.handler.AdvancementHandler;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileBloodyEnchantress extends GeneratingFlowerBlockEntity {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;
    private static final int START_BURN_EVENT = 0;

    private int burnTime = 0;

    public SubTileBloodyEnchantress(BlockPos pos, BlockState state) {
        super(ModSubtiles.BLOODY_ENCHANTRESS.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        // Re-bind to the nearest mana collector whenever unbound (Botania only auto-binds on the first tick)
        if (!level.isClientSide && (getBindingPos() == null || !isValidBinding())) {
            setBindingPos(findClosestTarget());
        }


        if(burnTime > 0)
            burnTime--;

        int ampall = 0;
        for(LivingEntity living : getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))){
            if(!living.isRemoved()){
                int amp = living.hasEffect(ModPotions.bloodtemptation.get()) ? living.getEffect(ModPotions.bloodtemptation.get()).getAmplifier() : 0;
                ampall += amp;
            }
        }
        if(ampall > 35)
            return;

        if(findBoundTile() != null) {
            if(burnTime == 0) {
                if(getMana() < getMaxMana()) {
                    for(LivingEntity living : getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))){
                        if(!living.isRemoved()){
                            int amp = living.hasEffect(ModPotions.bloodtemptation.get()) ? living.getEffect(ModPotions.bloodtemptation.get()).getAmplifier() : 0;
                            if(amp > 4 && Math.random() > 0.5F)
                                continue;
                            if(amp < 10){
                                addMana((int) (22F * 12F * (1F - 0.04F * amp - 0.02F * ampall)));
                            }else
                                break;
                            ExtraBotanyAPI.addPotionEffect(living, ModPotions.bloodtemptation.get(), 100, 10, true);
                            if(living instanceof ServerPlayer player){
                                AdvancementHandler.INSTANCE.grantAdvancement(player, LibAdvancementNames.BLOODYENCHANTRESSUSE);
                            }
                            DamageSource magic = living.damageSources().magic();
                            living.hurt(magic, 3F);
                            living.hurt(magic, 0.01F);
                            burnTime+=20;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public boolean triggerEvent(int event, int param) {
        if(event == START_BURN_EVENT) {
            Entity e = getLevel().getEntity(param);
            if(e != null) {
                getLevel().addParticle(ParticleTypes.LARGE_SMOKE, e.getX(), e.getY() + 0.1, e.getZ(), 0.0D, 0.0D, 0.0D);
                getLevel().addParticle(ParticleTypes.FLAME, e.getX(), e.getY(), e.getZ(), 0.0D, 0.0D, 0.0D);
            }
            return true;
        } else {
            return super.triggerEvent(event, param);
        }
    }

    @Override
    public int getMaxMana() {
        return 800;
    }

    @Override
    public int getColor() {
        return 0x8B0000;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
