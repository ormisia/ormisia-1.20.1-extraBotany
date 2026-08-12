package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class FlamescionStateUpdatePack {

    private int energy;
    private boolean overloaded;

    public FlamescionStateUpdatePack(FriendlyByteBuf buffer) {
        energy = buffer.readInt();
        overloaded = buffer.readBoolean();
    }

    public FlamescionStateUpdatePack(int energy, boolean overloaded) {
        this.energy = energy;
        this.overloaded = overloaded;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.energy);
        buf.writeBoolean(this.overloaded);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                LocalPlayer player = Minecraft.getInstance().player;
                LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(this.energy);
                    c.setOverloaded(this.overloaded);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
