package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.handler.AdvancementHandler;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemNightmareFuel extends Item {

    public ItemNightmareFuel(Properties prop) {
        super(prop);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerentity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (playerentity != null) {
            playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (entityLiving instanceof ServerPlayer)
                AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayer) playerentity, LibAdvancementNames.NIGHTMAREFUELEAT);
        }

        if (playerentity == null || !playerentity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.spiritfuel.get());
            }

            if (playerentity != null) {
                playerentity.getInventory().add(new ItemStack(ModItems.spiritfuel.get()));
            }
        }

        return stack;
    }

}
