package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.items.relic.ItemBuddhistrelics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class BuddhistChangePack {

    public BuddhistChangePack(FriendlyByteBuf buffer) {

    }

    public BuddhistChangePack() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                if(!ItemBuddhistrelics.relicShift(player.getMainHandItem()).isEmpty())
                    ctx.get().enqueueWork(() -> player.setItemInHand(InteractionHand.MAIN_HAND, ItemBuddhistrelics.relicShift(player.getMainHandItem())));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
