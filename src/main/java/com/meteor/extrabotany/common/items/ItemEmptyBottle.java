package com.meteor.extrabotany.common.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

import javax.annotation.Nonnull;

public class ItemEmptyBottle extends Item {

    public ItemEmptyBottle(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        BlockEntity tile = ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        if (tile instanceof ManaPoolBlockEntity) {
            ManaPoolBlockEntity pool = (ManaPoolBlockEntity) tile;
            if (!ctx.getLevel().isClientSide && pool.getCurrentMana() >= 25000) {
                pool.receiveMana(-25000);
                if (!ctx.getPlayer().getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (stack.isEmpty()) {
                    ctx.getPlayer().setItemInHand(ctx.getHand(), new ItemStack(ModItems.manadrink.get()));
                } else {
                    ctx.getPlayer().getInventory().add(new ItemStack(ModItems.manadrink.get()));
                }

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
