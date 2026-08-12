package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ItemChallengeTicket extends Item {

    public ItemChallengeTicket(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        //EntityEGOMinion.spawn(worldIn, player.getPosition(), 60);
        //EntityEGOLandmine.spawnLandmine(7, worldIn, player.getPosition(), null);
        return InteractionResultHolder.pass(itemstack);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        return EntityEGO.spawn(ctx.getPlayer(), stack, ctx.getLevel(), ctx.getClickedPos()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        //return InteractionResult.CONSUME;
    }
}
