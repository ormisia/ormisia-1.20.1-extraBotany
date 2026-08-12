package com.meteor.extrabotany.api.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface IItemWithLeftClick {

    public void onLeftClick(Player living, Entity target);

}
