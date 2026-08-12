package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.entities.projectile.EntityAuraFire;
import com.meteor.extrabotany.common.network.LeftClickPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemJingweiFeather extends ItemBauble implements IItemWithLeftClick {

    public static final int MANA_PER_DAMAGE = 300;

    public ItemJingweiFeather(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntity().level().isClientSide) {
            if(!EquipmentHandler.findOrEmpty(this, evt.getEntity()).isEmpty())
                onLeftClick(evt.getEntity(), evt.getTarget());
        }
    }

    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if(!EquipmentHandler.findOrEmpty(this, evt.getEntity()).isEmpty())
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(EquipmentHandler.findOrEmpty(this, evt.getEntity())));

    }

    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if(evt.getEntity().level().isClientSide && !EquipmentHandler.findOrEmpty(this, evt.getEntity()).isEmpty())
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(EquipmentHandler.findOrEmpty(this, evt.getEntity())));
    }

    @Override
    public void onLeftClick(Player living, Entity target) {
        if(living.getMainHandItem().isEmpty() && living.getAttackStrengthScale(0.0F) == 1)
            if(ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), living, MANA_PER_DAMAGE, true)){
                EntityAuraFire proj = new EntityAuraFire(living.level(), living);
                proj.setPos(living.getX(), living.getY()+1.1D, living.getZ());
                proj.shootFromRotation(living, living.getXRot(), living.getYRot(), 0.0F, 0.8F, 0.9F);
                if(!living.level().isClientSide)
                    living.level().addFreshEntity(proj);
            }
    }

}
