package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

public class HerrscherGUI {

    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/gui/hud.png");
    private GuiGraphics gui;
    private int offset;

    public HerrscherGUI(GuiGraphics gui, int offset) {
        this.offset = offset;
        this.gui = gui;
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        this.minecraft = Minecraft.getInstance();
    }

    public void render() {
        LazyOptional<IHerrscherEnergy> cap = this.minecraft.player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        cap.ifPresent((c) -> {
            int energy = c.getEnergy();
            renderBar(energy);
        });
    }

    private void renderBar(int energy) {
        Minecraft mc = Minecraft.getInstance();
        int width = 64;
        int x = mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 56 - offset;

        width *= (double) energy / 600D;

        gui.blit(HUD, x, y, 0, 0, 64, 6);
        if (energy < 600)
            gui.blit(HUD, x, y, 0, 6, width, 6);
        else
            gui.blit(HUD, x, y, 0, 11, 64, 6);
    }

}
