package com.meteor.extrabotany.common.items.bauble.mount;

import com.meteor.extrabotany.api.items.IMountableAccessory;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.items.bauble.ItemBauble;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ItemCosmicCarKeyAccessory extends ItemBauble implements IMountableAccessory {

    public ItemCosmicCarKeyAccessory(Properties props) {
        super(props);
    }

    @Override
    public Entity getMountableEntity(Level world) {
        EntityUfo ufo = new EntityUfo(world);
        ufo.setDeltaMovement(0, 0, 0);
        ufo.setMountable(true);
        return ufo;
    }

}
