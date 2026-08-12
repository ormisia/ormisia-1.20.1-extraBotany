package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelKeyOfTruth;
import com.meteor.extrabotany.common.entities.EntityKeyOfTruth;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RenderKeyOfTruth extends EntityRenderer<EntityKeyOfTruth> {

    private EntityModel<Entity> motorModel = new ModelKeyOfTruth();

    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/energybeam.png");
    private static final RenderType field_229107_h_ = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_TEXTURE);

    public RenderKeyOfTruth(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityKeyOfTruth entity) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/keyoftruth.png");
    }

    @Override
    public void render(EntityKeyOfTruth entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 1.375D, 0.0D);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(entityIn.getRotation()));
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(entityIn.getPitch()));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.motorModel.renderType(this.getTextureLocation(entityIn)));
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        this.motorModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.65F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.popPose();

        if (entityIn.getTarget() != -1 && entityIn.getShoot()) {
            Entity livingentity = entityIn.level().getEntity(entityIn.getTarget());
            if (livingentity != null && livingentity.isAlive()) {
                float f = 0.5F;
                float f1 = (float) entityIn.level().getGameTime() + partialTicks;
                float f2 = f1 * 0.5F % 1.0F;
                float f3 = entityIn.getEyeHeight();
                matrixStackIn.pushPose();
                matrixStackIn.translate(0.0D, (double) f3, 0.0D);
                Vec3 vec3d = this.getPosition(livingentity, (double) livingentity.getBbHeight() * 0.5D, partialTicks);
                Vec3 vec3d1 = this.getPosition(entityIn, (double) f3, partialTicks);
                Vec3 vec3d2 = vec3d.subtract(vec3d1);
                float f4 = (float) (vec3d2.length() + 1.0D);
                vec3d2 = vec3d2.normalize();
                float f5 = (float) Math.acos(vec3d2.y);
                float f6 = (float) Math.atan2(vec3d2.z, vec3d2.x);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));
                int i = 1;
                float f7 = f1 * 0.05F * -1.5F;
                float f8 = f * f;
                int j = 10;
                int k = 229;
                int l = 238;
                float f9 = 0.2F;
                float f10 = 0.282F;
                float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
                float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
                float f13 = Mth.cos(f7 + ((float) Math.PI / 4F)) * 0.282F;
                float f14 = Mth.sin(f7 + ((float) Math.PI / 4F)) * 0.282F;
                float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
                float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
                float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
                float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
                float f19 = Mth.cos(f7 + (float) Math.PI) * 0.2F;
                float f20 = Mth.sin(f7 + (float) Math.PI) * 0.2F;
                float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
                float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
                float f23 = Mth.cos(f7 + ((float) Math.PI / 2F)) * 0.2F;
                float f24 = Mth.sin(f7 + ((float) Math.PI / 2F)) * 0.2F;
                float f25 = Mth.cos(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
                float f26 = Mth.sin(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
                float f27 = 0.0F;
                float f28 = 0.4999F;
                float f29 = -1.0F + f2;
                float f30 = f4 * 2.5F + f29;
                VertexConsumer ivertexbuilder2 = bufferIn.getBuffer(field_229107_h_);
                PoseStack.Pose matrixstack$entry = matrixStackIn.last();
                Matrix4f matrix4f = matrixstack$entry.pose();
                Matrix3f matrix3f = matrixstack$entry.normal();
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
                float f31 = 0.0F;

                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
                func_229108_a_(ivertexbuilder2, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
                matrixStackIn.popPose();
            }
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private Vec3 getPosition(Entity entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
        double d0 = Mth.lerp((double) p_177110_4_, entityLivingBaseIn.xo, entityLivingBaseIn.getX());
        double d1 = Mth.lerp((double) p_177110_4_, entityLivingBaseIn.yo, entityLivingBaseIn.getY()) + p_177110_2_;
        double d2 = Mth.lerp((double) p_177110_4_, entityLivingBaseIn.zo, entityLivingBaseIn.getZ());
        return new Vec3(d0, d1, d2);
    }

    private static void func_229108_a_(VertexConsumer p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_) {
        p_229108_0_.vertex(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).color(p_229108_6_, p_229108_7_, p_229108_8_, 255).uv(p_229108_9_, p_229108_10_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_229108_2_, 0.0F, 1.0F, 0.0F).endVertex();
    }

}
