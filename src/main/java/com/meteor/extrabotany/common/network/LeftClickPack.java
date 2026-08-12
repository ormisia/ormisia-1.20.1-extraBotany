package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class LeftClickPack {

    private ItemStack stack;

    public LeftClickPack(FriendlyByteBuf buffer) {
        stack = buffer.readItem();
    }

    public LeftClickPack(ItemStack stack) {
        this.stack = stack;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(stack);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                if(stack.getItem() instanceof IItemWithLeftClick){
                    IItemWithLeftClick item = (IItemWithLeftClick) stack.getItem();
                    ctx.get().enqueueWork(() -> item.onLeftClick(player, null));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
