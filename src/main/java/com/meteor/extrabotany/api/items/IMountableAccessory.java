package com.meteor.extrabotany.api.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface IMountableAccessory {

    public Entity getMountableEntity(Level world);

}
