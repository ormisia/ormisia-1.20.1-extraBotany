package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class UfoCatchPack {

    private int id;

    public UfoCatchPack(FriendlyByteBuf buffer) {
        id = buffer.readInt();
    }

    public UfoCatchPack(int id) {
        this.id = id;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                Entity riding = player.getVehicle();
                if (riding != null && riding instanceof EntityUfo) {
                    EntityUfo motor = (EntityUfo) riding;
                    motor.setCatchedID(id);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
