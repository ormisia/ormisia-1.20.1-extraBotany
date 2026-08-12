package com.meteor.extrabotany.common.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.xplat.XplatAbstractions;

@Mod.EventBusSubscriber
public final class EventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        ManaItem item = XplatAbstractions.INSTANCE.findManaItem(stack);
        if(item != null){
            event.getToolTip().add(Component.literal("Mana:" + item.getMana() + "/" + item.getMaxMana()));
        }
    }

}
