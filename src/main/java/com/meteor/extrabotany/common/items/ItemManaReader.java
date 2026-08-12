package com.meteor.extrabotany.common.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

import javax.annotation.Nonnull;

public class ItemManaReader extends Item {

    public ItemManaReader(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockEntity tile = ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        Player player = ctx.getPlayer();
        int mana = 0;
        if (tile instanceof ManaPoolBlockEntity) {
            ManaPoolBlockEntity pool = (ManaPoolBlockEntity) tile;
            mana = pool.getCurrentMana();
        } else if (tile instanceof ManaReceiver) {
            ManaReceiver t = (ManaReceiver) tile;
            mana = t.getCurrentMana();
        }
        if (!ctx.getLevel().isClientSide)
            player.sendSystemMessage(Component.literal(String.format("Mana:%d", mana)));
        return InteractionResult.PASS;
    }
}
