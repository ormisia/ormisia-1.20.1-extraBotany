package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.api.items.IMountableAccessory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class MountPack {

    private ItemStack stack;

    public MountPack(FriendlyByteBuf buffer) {
        stack = buffer.readItem();
    }

    public MountPack(ItemStack stack) {
        this.stack = stack;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(stack);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                if(stack.getItem() instanceof IMountableAccessory){
                    IMountableAccessory mountable = (IMountableAccessory) stack.getItem();
                    Entity mount = mountable.getMountableEntity(player.level());
                    mount.setPos(player.getX(), player.getY()+0.5F, player.getZ());
                    mount.setYRot(player.getYRot());
                    if(player.level().addFreshEntity(mount)){
                        player.startRiding(mount);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
