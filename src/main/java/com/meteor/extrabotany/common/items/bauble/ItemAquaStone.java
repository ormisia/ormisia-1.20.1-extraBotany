package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaDiscountEvent;

public class ItemAquaStone extends ItemBauble{

    public ItemAquaStone(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::manaDiscount);
    }

    @SubscribeEvent
    public void manaDiscount(ManaDiscountEvent event){
        Player player = event.getEntityPlayer();
        if(!EquipmentHandler.findOrEmpty(this, player).isEmpty() || !EquipmentHandler.findOrEmpty(ModItems.thecommunity.get(), player).isEmpty()){
            event.setDiscount(event.getDiscount() + 0.1F);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ModItems.thecommunity.get(), entity).isEmpty();
    }

}
