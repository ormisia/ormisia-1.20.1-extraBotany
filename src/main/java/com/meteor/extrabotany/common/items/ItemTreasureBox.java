package com.meteor.extrabotany.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemTreasureBox extends Item {

    public ItemTreasureBox(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (!worldIn.isClientSide) {
            player.spawnAtLocation(new ItemStack(ModItems.rewardbaga.get(), 32)).setNoPickUpDelay();
            player.spawnAtLocation(new ItemStack(ModItems.rewardbagb.get(), 16)).setNoPickUpDelay();
            player.spawnAtLocation(new ItemStack(ModItems.rewardbagc.get(), 10)).setNoPickUpDelay();
            player.spawnAtLocation(new ItemStack(ModItems.rewardbagd.get(), 10)).setNoPickUpDelay();
            player.spawnAtLocation(new ItemStack(ModItems.heromedal.get())).setNoPickUpDelay();
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

}
