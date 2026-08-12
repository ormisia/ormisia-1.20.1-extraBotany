package com.meteor.extrabotany.common.items.armor.shootingguardian;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemShootingGuardianHelm extends ItemShootingGuardianArmor{

    public ItemShootingGuardianHelm(Properties props) {
        super(ArmorItem.Type.HELMET, props);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttack);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerUseBow);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerHeal);
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingHurtEvent event){
        if(event.getSource().getEntity() instanceof Player player){
            if (hasArmorSet(player)) {
                player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + event.getAmount() * 0.2F));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUseBow(LivingEntityUseItemEvent event){
        if(event.getEntity() instanceof Player player){
            if(event.getItem().getItem() instanceof BowItem && hasArmorSet(player)){
                event.setDuration(event.getDuration() - 1);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event){
        if(event.getEntity() instanceof Player player){
            if(hasArmorSet(player))
                event.setAmount(event.getAmount() * 0.2F);
        }
    }

}
