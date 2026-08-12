package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.CuriosApi;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.helper.PlayerHelper;

import java.util.Map;

@Mod.EventBusSubscriber
public final class AdvancementHandler {

    public static final AdvancementHandler INSTANCE = new AdvancementHandler();

    public void grantAdvancement(ServerPlayer player, String id){
        PlayerHelper.grantCriterion(player, new ResourceLocation(LibMisc.MOD_ID, "main/" + id), "code_triggered");
    }

    public static boolean checkAdvancement(Player player, String modid, String advancement){
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if(id != null) {
            if (player instanceof ServerPlayer) {
                ServerAdvancementManager manager = player.getServer().getAdvancements();
                PlayerAdvancements advancements = ((ServerPlayer) player).getAdvancements();
                Advancement adv = manager.getAdvancement(id);
                if(adv != null)
                    return advancements.getOrStartProgress(adv).isDone();
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static Advancement getSideAdvancement(String modid, String advancement){
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if(id != null){
            ClientPacketListener netHandler = Minecraft.getInstance().getConnection();
            ClientAdvancements manager = netHandler.getAdvancements();
            Advancement adv = manager.getAdvancements().get(id);
            return adv;
        }
        return null;
    }

    public static boolean hasDone(String modid, String advancement) {
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if (id != null) {
            ClientPacketListener conn = Minecraft.getInstance().getConnection();
            if (conn != null) {
                ClientAdvancements cm = conn.getAdvancements();
                Advancement adv = cm.getAdvancements().get(id);
                if (adv != null) {
                    Map<Advancement, AdvancementProgress> progressMap = ObfuscationReflectionHelper.getPrivateValue(ClientAdvancements.class, cm, "progress");
                    AdvancementProgress progress = progressMap.get(adv);
                    return progress != null && progress.isDone();
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelable() && !event.getEntity().isCreative()) {
            if (event.getItemStack().getItem() instanceof IAdvancementRequirement) {
                IAdvancementRequirement r = (IAdvancementRequirement) event.getItemStack().getItem();
                if (!checkAdvancement(event.getEntity(), LibMisc.MOD_ID, r.getAdvancementName()))
                    event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IAdvancementRequirement) {
            IAdvancementRequirement r = (IAdvancementRequirement) event.getItemStack().getItem();
            LocalPlayer playerSP = Minecraft.getInstance().player;
            if (playerSP != null) {
                if (!hasDone(LibMisc.MOD_ID, r.getAdvancementName()))
                    event.getToolTip().add(Component.translatable("extrabotanymisc.description",
                            Component.translatable("extrabotany." + r.getAdvancementName() + ".title")).withStyle(ChatFormatting.RED));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player && !event.getEntity().level().isClientSide) {
            final Player player = (Player) event.getEntity();
            if (player.isCreative()) {
                return;
            }

            CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent((c) ->{
                for(int i = 0; i < c.getSlots(); i++){
                    final ItemStack stack = c.getStackInSlot(i);
                    if (stack.getItem() instanceof IAdvancementRequirement) {
                        IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                        if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                            c.setStackInSlot(i, ItemStack.EMPTY);
                            player.drop(stack, false);
                        }
                    }
                }
            });

            for (final EquipmentSlot slot : EquipmentSlot.values()) {
                final ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof IAdvancementRequirement) {
                    IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                    if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                        player.setItemSlot(slot, ItemStack.EMPTY);
                        player.drop(stack, false);
                    }
                }
            }
        }

    }

}
