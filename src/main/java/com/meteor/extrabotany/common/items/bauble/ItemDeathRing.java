package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.handler.DamageHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemDeathRing extends ItemBauble {

    public ItemDeathRing(Properties props) {
        super(props);
    }

    private static final int RANGE = 6;
    private static final int MANA_PER_DAMAGE = 80;

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(entity instanceof Player) {
            if (!entity.level().isClientSide) {
                for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.blockPosition().offset(-RANGE, -RANGE, -RANGE), entity.blockPosition().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                    if (!living.isInvisibleTo((Player) entity)
                            && living != entity
                            && DamageHandler.INSTANCE.checkPassable(living, entity)
                            && ManaItemHandler.instance().requestManaExactForTool(stack, (Player) entity, MANA_PER_DAMAGE, true)
                            && entity.tickCount % 30 == 0) {
                        living.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
                        living.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 60, 1));
                        DamageHandler.INSTANCE.dmg(living, entity, 0.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    }
                }
            }
        }
    }

}
