package com.meteor.extrabotany.common.items.bauble.mount;

import com.meteor.extrabotany.api.items.IMountableAccessory;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.item.relic.RelicBaubleItem;

public class ItemMotorAccessory extends RelicBaubleItem implements IMountableAccessory {

    public ItemMotorAccessory(Properties props) {
        super(props);
    }

    @Override
    public Entity getMountableEntity(Level world) {
        EntityMotor motor = new EntityMotor(world);
        motor.setDeltaMovement(0, 0, 0);
        motor.setMountable(true);
        return motor;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }
}
