package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.ExtraBotanyGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemGemOfConquest extends Item {

    public ItemGemOfConquest() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).setNoRepair());
    }

}
