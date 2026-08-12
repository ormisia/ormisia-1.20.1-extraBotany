package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;

public class HUDHandler {

    public static void onOverlayRender(RenderGuiOverlayEvent.Post event) {
        // Only draw our custom HUD once per frame; the crosshair overlay is a reliable hook for this.
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlay == null || !"minecraft".equals(overlay.id().getNamespace()) || !"crosshair".equals(overlay.id().getPath())) {
            return;
        }

        int offset = 0;

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        if (HerrscherHandler.isHerrscherOfThunder(player)) {
            HerrscherGUI gui = new HerrscherGUI(event.getGuiGraphics(), offset);
            gui.render();
            offset += 7;
        }

        Entity riding = player.getVehicle();

        if (riding != null) {
            if (riding instanceof EntityMotor) {
                MotorGUI motorGui = new MotorGUI(event.getGuiGraphics(), offset);
                motorGui.render();
                offset += 7;
            }
        }

        if (riding == null && !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == FlamescionHandler.getFlamescionWeapon()) {
            FlamescionGUI flamescionGUI = new FlamescionGUI(event.getGuiGraphics(), offset);
            flamescionGUI.render();
            offset += 7;
        }
    }

}
