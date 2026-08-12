package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

public class FlamescionGUI {

    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/gui/flamescionhud.png");
    private GuiGraphics gui;
    private int offset;

    public FlamescionGUI(GuiGraphics gui, int offset) {
        this.offset = offset;
        this.gui = gui;
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        this.minecraft = Minecraft.getInstance();
    }

    public void render() {
        LazyOptional<IFlamescion> cap = this.minecraft.player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        cap.ifPresent((c) -> {
            int energy = c.getEnergy();
            boolean overloaded = c.isOverloaded();
            renderBar(energy, overloaded);
        });
    }

    private void renderBar(int energy, boolean overloaded) {
        Minecraft mc = Minecraft.getInstance();
        int width = 64;
        int x = mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 56 - offset;

        width *= (double) energy / FlamescionHandler.MAX_FLAMESCION_ENERGY;

        gui.blit(HUD, x, y, 0, 0, 64, 6);
        if (!overloaded)
            gui.blit(HUD, x, y, 0, 6, width, 6);
        else
            gui.blit(HUD, x, y, 0, 12, width, 6);
    }
}
