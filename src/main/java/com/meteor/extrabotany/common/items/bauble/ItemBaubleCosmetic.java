package com.meteor.extrabotany.common.items.bauble;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.client.render.AccessoryRenderer;

import java.util.List;

public class ItemBaubleCosmetic extends ItemBauble {

    public enum Variant {
        FOX_EAR(true), FOX_MASK(true), PYLON(true), BLACK_GLASSES(true), RED_SCARF, SUPER_CROWN(true), THUG_LIFE(true), MASK(true);
        private final boolean isHead;

        Variant(boolean isHead) {
            this.isHead = isHead;
        }

        Variant() {
            this(false);
        }
    }

    private final Variant variant;
    public static final int SUBTYPES = Variant.values().length;

    public ItemBaubleCosmetic(Variant variant, Properties props) {
        super(props);
        this.variant = variant;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        Variant variant = ((ItemBaubleCosmetic) stack.getItem()).variant;
        if(variant == Variant.FOX_MASK) {
            tooltip.add(Component.translatable("extrabotany.foxmaskinfo0").withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("extrabotany.foxmaskinfo1").withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("extrabotany.foxmaskinfo2").withStyle(ChatFormatting.ITALIC));
        }
    }

    @Override
    public boolean hasRender(ItemStack stack, LivingEntity player) {
        return true;
    }

    public static void renderItem(ItemStack stack, PoseStack ms, MultiBufferSource buffers, int light) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, ms, buffers, Minecraft.getInstance().level, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer implements AccessoryRenderer {

        @Override
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity player, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            Variant variant = ((ItemBaubleCosmetic) stack.getItem()).variant;
            if (variant.isHead) {
                bipedModel.head.translateAndRotate(ms);
                switch (variant) {
                    case FOX_EAR:
                        ms.pushPose();
                        ms.translate(0, -0.8, -0.1F);
                        ms.scale(0.8F, -0.8F, -0.8F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case FOX_MASK:
                        ms.pushPose();
                        ms.translate(0.02F, -0.3, -0.3);
                        ms.scale(0.66F, -0.65F, -0.65F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case MASK:
                        ms.pushPose();
                        ms.translate(0F, -0.3, -0.3);
                        ms.scale(0.65F, -0.65F, -0.65F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case BLACK_GLASSES:
                        ms.pushPose();
                        ms.translate(0, -0.2, -0.3);
                        ms.scale(0.55F, 0.55F, -0.55F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case SUPER_CROWN:
                        ms.pushPose();
                        ms.translate(0, -0.7, -0.1F);
                        ms.scale(0.65F, -0.65F, -0.65F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case PYLON:
                        ms.pushPose();
                        ms.translate(0, -0.8, -0.1F);
                        ms.scale(0.5F, -0.5F, -0.5F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                    case THUG_LIFE:
                        ms.pushPose();
                        ms.translate(0, -0.2, -0.3);
                        ms.scale(0.7F, -0.7F, -0.7F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                }
            }else {
                bipedModel.body.translateAndRotate(ms);
                switch (variant) {
                    case RED_SCARF:
                        ms.pushPose();
                        ms.translate(0, 0.16, -0.15);
                        ms.scale(0.55F, -0.55F, -0.55F);
                        renderItem(stack, ms, buffers, light);
                        ms.popPose();
                        break;
                }
            }
        }

    }

}
