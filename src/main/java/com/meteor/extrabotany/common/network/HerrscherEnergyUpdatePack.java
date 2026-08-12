package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class HerrscherEnergyUpdatePack {

    private int energy;

    public HerrscherEnergyUpdatePack(FriendlyByteBuf buffer) {
        energy = buffer.readInt();
    }

    public HerrscherEnergyUpdatePack(int energy) {
        this.energy = energy;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.energy);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                LocalPlayer player = Minecraft.getInstance().player;
                LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(this.energy);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
