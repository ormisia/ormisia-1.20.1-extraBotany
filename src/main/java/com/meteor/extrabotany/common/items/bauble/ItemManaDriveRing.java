package com.meteor.extrabotany.common.items.bauble;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemManaDriveRing extends ItemBauble {

    public ItemManaDriveRing(Properties props) {
        super(props);
    }

    private static final int RANGE = 7;
    private static final int MANA_PER_FILL = 100;

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        if(player.tickCount % 20 == 0)
            for(int x = -RANGE; x <= RANGE; x++)
                for(int y = -RANGE; y <= RANGE; y++)
                    for(int z = -RANGE; z <= RANGE; z++) {
                        BlockEntity te = player.level().getBlockEntity(new BlockPos(player.blockPosition().offset(x, y, z)));
                        if(te instanceof FunctionalFlowerBlockEntity) {
                            FunctionalFlowerBlockEntity f = (FunctionalFlowerBlockEntity) te;
                            // 1.20.1 FunctionalFlowerBlockEntity no longer exposes getMaxMana();
                            // charge a fixed amount per scan instead of topping the flower up.
                            if(ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_FILL, true))
                                f.addMana(MANA_PER_FILL);
                        }
                    }
    }

}
