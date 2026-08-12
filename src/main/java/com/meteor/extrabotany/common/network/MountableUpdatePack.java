package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.mountable.EntityMountable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class MountableUpdatePack {

    private boolean ctrlInputDown;
    private boolean upInputDown;

    public MountableUpdatePack(FriendlyByteBuf buffer) {
        ctrlInputDown = buffer.readBoolean();
        upInputDown = buffer.readBoolean();
    }

    public MountableUpdatePack(boolean ctrlInputDown, boolean upInputDown) {
        this.ctrlInputDown = ctrlInputDown;
        this.upInputDown = upInputDown;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.ctrlInputDown);
        buf.writeBoolean(this.upInputDown);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                Entity riding = player.getVehicle();
                if (riding != null && riding instanceof EntityMountable) {
                    EntityMountable motor = (EntityMountable) riding;
                    motor.updateInput(ctrlInputDown, upInputDown);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
