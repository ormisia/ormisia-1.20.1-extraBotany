package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.api.items.IMountableAccessory;
import com.meteor.extrabotany.common.entities.mountable.EntityMountable;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.items.relic.ItemBuddhistrelics;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.handler.EquipmentHandler;

import java.util.List;

@Mod.EventBusSubscriber
public class KeyInputHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (e.phase == TickEvent.Phase.END && mc.player != null && mc.player.getAttackStrengthScale(0) == 1
                && mc.options.keyAttack.isDown()) {
            if(!EquipmentHandler.findOrEmpty(ModItems.powerglove.get(), mc.player).isEmpty()) {
                if (mc.hitResult.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult result = (EntityHitResult) mc.hitResult;
                    Entity entity = result.getEntity();
                    mc.gameMode.attack(mc.player, entity);
                }else if(mc.hitResult.getType() == HitResult.Type.BLOCK){
                    BlockHitResult blockraytraceresult = (BlockHitResult) mc.hitResult;
                    BlockPos blockpos = blockraytraceresult.getBlockPos();
                    if (!mc.player.level().isEmptyBlock(blockpos)) {
                        mc.gameMode.startDestroyBlock(blockpos, blockraytraceresult.getDirection());
                    }
                }else if(mc.hitResult.getType() == HitResult.Type.MISS){
                    mc.player.resetAttackStrengthTicker();
                    ForgeHooks.onEmptyLeftClick(mc.player);
                    mc.player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player p = Minecraft.getInstance().player;
        if(p == null)
            return;
        if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL) {
            if (!ItemBuddhistrelics.relicShift(p.getMainHandItem()).isEmpty()) {
                NetworkHandler.INSTANCE.sendToServer(new BuddhistChangePack());
            }
        }
        Entity riding = p.getVehicle();
        if(riding == null){
            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R) {
                ItemStack mountable = EquipmentHandler.findOrEmpty((stack) -> stack.getItem() instanceof IMountableAccessory, p);
                if (!mountable.isEmpty()) {
                    NetworkHandler.INSTANCE.sendToServer(new MountPack(mountable));
                    return;
                }
            }
        }
        if (riding instanceof EntityMountable) {
            EntityMountable steerable = (EntityMountable) riding;
            steerable.updateInput(ExtraBotany.keyFlight.isDown(), ExtraBotany.keyUp.isDown());
            NetworkHandler.INSTANCE.sendToServer(new MountableUpdatePack(ExtraBotany.keyFlight.isDown(), ExtraBotany.keyUp.isDown()));
        }
        if (riding instanceof EntityUfo) {
            EntityUfo steerable = (EntityUfo) riding;

            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R){

                if(steerable.getCatchedID() != -1){
                    steerable.setCatchedID(-1);
                    NetworkHandler.INSTANCE.sendToServer(new UfoCatchPack(-1));
                    return;
                }else{
                    List<LivingEntity> entities = steerable.getEntitiesBelow();
                    if(entities.size() > 0) {
                        int id = -1;
                        float distance = 16F;
                        for(Entity e : entities){
                            if(e == p)
                                continue;
                            if(e.distanceTo(steerable) < distance){
                                distance = e.distanceTo(steerable);
                                id = e.getId();
                            }
                        }
                        steerable.setCatchedID(id);
                        NetworkHandler.INSTANCE.sendToServer(new UfoCatchPack(id));
                    }
                }
            }
        }
    }

}
