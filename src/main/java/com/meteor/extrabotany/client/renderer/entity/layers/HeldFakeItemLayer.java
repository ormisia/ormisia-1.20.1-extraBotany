package com.meteor.extrabotany.client.renderer.entity.layers;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.entities.ego.EntityEGOMinion;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldFakeItemLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {

    public HeldFakeItemLayer(RenderLayerParent<T, M> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        boolean lvt_11_1_ = p_225628_4_.getMainArm() == HumanoidArm.RIGHT;
        ItemStack mainHand = ItemStack.EMPTY;
        ItemStack offHand = ItemStack.EMPTY;

        if (p_225628_4_ instanceof EntityEGOMinion) {
            EntityEGOMinion minion = (EntityEGOMinion) p_225628_4_;
            mainHand = minion.getWeapon();
        }

        if (p_225628_4_ instanceof EntityEGO) {
            EntityEGO minion = (EntityEGO) p_225628_4_;
            mainHand = minion.getWeapon();
        }

        ItemStack lvt_12_1_ = lvt_11_1_ ? offHand : mainHand;
        ItemStack lvt_13_1_ = lvt_11_1_ ? mainHand : offHand;

        if (!lvt_12_1_.isEmpty() || !lvt_13_1_.isEmpty()) {
            p_225628_1_.pushPose();
            if (p_225628_4_.isBaby()) {
                float lvt_14_1_ = 0.5F;
                p_225628_1_.translate(0.0D, 0.75D, 0.0D);
                p_225628_1_.scale(0.5F, 0.5F, 0.5F);
            }

            this.func_229135_a_(p_225628_4_, lvt_13_1_, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, p_225628_1_, p_225628_2_, p_225628_3_);
            this.func_229135_a_(p_225628_4_, lvt_12_1_, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, p_225628_1_, p_225628_2_, p_225628_3_);
            p_225628_1_.popPose();
        }
    }

    private void func_229135_a_(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemDisplayContext p_229135_3_, HumanoidArm p_229135_4_, PoseStack p_229135_5_, MultiBufferSource p_229135_6_, int p_229135_7_) {
        if (!p_229135_2_.isEmpty()) {
            p_229135_5_.pushPose();
            ((ArmedModel) this.getParentModel()).translateToHand(p_229135_4_, p_229135_5_);
            p_229135_5_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            p_229135_5_.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean lvt_8_1_ = p_229135_4_ == HumanoidArm.LEFT;
            p_229135_5_.translate((double) ((float) (lvt_8_1_ ? -1 : 1) / 16.0F), 0.125D, -0.625D);
            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, lvt_8_1_, p_229135_5_, p_229135_6_, p_229135_7_);
            p_229135_5_.popPose();
        }
    }
}
