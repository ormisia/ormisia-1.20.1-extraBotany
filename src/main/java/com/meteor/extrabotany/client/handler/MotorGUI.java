package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MotorGUI {

    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/gui/motorhud.png");
    private GuiGraphics gui;
    private int offset;

    public MotorGUI(GuiGraphics gui, int offset) {
        this.offset = offset;
        this.gui = gui;
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        this.minecraft = Minecraft.getInstance();
    }

    public void render() {
        Player player = minecraft.player;
        Entity riding = player.getVehicle();
        if (riding != null) {
            if (riding instanceof EntityMotor) {
                EntityMotor motor = (EntityMotor) riding;
                renderBar(motor.getTectonicEnergy());
            }
        }
    }

    private void renderBar(int energy) {
        Minecraft mc = Minecraft.getInstance();
        int width = 64;
        int x = mc.getWindow().getGuiScaledWidth() / 2 - width / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 56 - offset;

        width *= (double) energy / 800D;

        gui.blit(HUD, x, y, 0, 0, 64, 6);
        gui.blit(HUD, x, y, 0, 6, width, 6);
    }
}
