package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class DamageHandler {

    public static final DamageHandler INSTANCE = new DamageHandler();

    public final int NETURAL = 0;
    public final int MAGIC = 1;
    public final int NETURAL_PIERCING = 2;
    public final int MAGIC_PIERCING = 3;
    public final int LIFE_LOSING = 4;

    public boolean checkPassable(Entity target, Entity source){
        if(target == source)
            return false;
        if(source instanceof Player){
            Player sourcePlayer = (Player) source;
            boolean sourceEquipped = !EquipmentHandler.findOrEmpty(ModItems.peaceamulet.get(), sourcePlayer).isEmpty();
            if(target instanceof Player){
                Player targetPlayer = (Player) target;
                return !sourceEquipped && EquipmentHandler.findOrEmpty(ModItems.peaceamulet.get(), targetPlayer).isEmpty();
            }
            // 1.16 used target.isNonBoss(), which was always true for non-mob entities (the only case reaching this
            // branch), so it is dropped here.
            if(sourceEquipped && !(target instanceof Enemy))
                return false;
        }

        if(source instanceof Enemy){
            if(target instanceof Player)
                return true;
            return false;
        }

        return true;
    }

    public List<LivingEntity> getFilteredEntities(List<LivingEntity> entities, Entity source){
        List<LivingEntity> list = entities.stream().filter((living) -> checkPassable(living, source) && !living.isRemoved()).collect(Collectors.toList());
        return list;
    }

    public static float calcDamage(float orig, Player player){
        if(player == null)
            return orig;
        double value = 0F;
        return (float) (orig + value);
    }

    private static DamageSource piercingSource(Entity target, Entity source, String name) {
        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, name));
        Holder<DamageType> holder = target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
        return new DamageSource(holder, source, source);
    }

    public boolean dmg(Entity target, Entity source, float amount, int type){
        if(target == null || !checkPassable(target, source))
            return false;
        switch (type){
            case NETURAL: {
                if (source instanceof Player) {
                    DamageSource s = target.damageSources().playerAttack((Player) source);
                    return target.hurt(s, amount);
                } else if (source instanceof LivingEntity) {
                    DamageSource s = target.damageSources().mobAttack((LivingEntity) source);
                    return target.hurt(s, amount);
                } else {
                    return target.hurt(target.damageSources().generic(), amount);
                }
            }
            case MAGIC: {
                if(source == null){
                    return target.hurt(target.damageSources().magic(), amount);
                }else{
                    return target.hurt(target.damageSources().indirectMagic(source, source), amount);
                }
            }
            case NETURAL_PIERCING: {
                ((LivingEntity) target).hurtTime = 0;
                DamageSource s = piercingSource(target, source, "piercing");
                return target.hurt(s, amount);
            }
            case MAGIC_PIERCING: {
                ((LivingEntity) target).hurtTime = 0;
                DamageSource s = piercingSource(target, source, "magic_piercing");
                return target.hurt(s, amount);
            }
            case LIFE_LOSING:{
                if(!(target instanceof LivingEntity))
                    return false;
                LivingEntity living = (LivingEntity) target;
                float currentHealth = living.getHealth();
                float trueHealth = Math.max(1F, currentHealth - amount);
                living.setHealth(trueHealth);
                return dmg(target, source, 0.01F, NETURAL);
            }
        }
        return false;
    }

}
