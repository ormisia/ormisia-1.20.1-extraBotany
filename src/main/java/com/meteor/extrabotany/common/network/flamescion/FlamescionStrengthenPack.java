package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.items.ItemFlamescionWeapon;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class FlamescionStrengthenPack {

    public FlamescionStrengthenPack(FriendlyByteBuf buffer) {

    }

    public FlamescionStrengthenPack() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                // TODO: ModItems.flamescionweapon.get() must expose the Item (likely via .get() on a RegistryObject) once ModItems is ported
                ctx.get().enqueueWork(() -> ((ItemFlamescionWeapon) ModItems.flamescionweapon.get()).tryStrengthenAttack(player));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
