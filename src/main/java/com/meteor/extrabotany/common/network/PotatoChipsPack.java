package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class PotatoChipsPack {

    public PotatoChipsPack(FriendlyByteBuf buffer) {

    }

    public PotatoChipsPack() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                LocalPlayer player = Minecraft.getInstance().player;
                // TODO: ModItems.potatochips.get() must expose the Item once ModItems is ported
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.potatochips.get()));
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
