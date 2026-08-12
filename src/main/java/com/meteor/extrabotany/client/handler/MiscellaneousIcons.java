package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

public class MiscellaneousIcons {

    public static final MiscellaneousIcons INSTANCE = new MiscellaneousIcons();

    public final Material dimensionCatalystOverlay = mainAtlas("block/dimensioncatalyst");

    public final BakedModel[] firstFractalWeaponModels = new BakedModel[10];
    public final BakedModel[] strengthenSlashModel = new BakedModel[1];
    public final BakedModel[] flamescionringModel = new BakedModel[1];
    public final BakedModel[] influxwaverprojectileModel = new BakedModel[1];
    public final BakedModel[] trueterrabladeprojectileModel = new BakedModel[1];
    public final BakedModel[] trueshadowkatanaprojectileModel = new BakedModel[1];
    public final BakedModel[] coregodWingsModel = new BakedModel[4];
    public final BakedModel[] coregodModel = new BakedModel[1];
    public final BakedModel[] butterflyprojectileModel = new BakedModel[1];

    public void onModelRegister(ModelEvent.RegisterAdditional evt) {
        // TODO(1.20.1): the 1.16 code added dimensionCatalystOverlay's Material to the bakery's material set via
        // AccessorModelBakery. In 1.20.1 bare block-atlas textures are stitched through the block model that
        // references them; the Material field is kept for the overlay renderer, which is currently unused.
        for (int i = 0; i < 10; i++) {
            evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/sworddomain_" + i));
        }
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/strengthenslash"));
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/flamescionring"));
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/influxwaverprojectile"));
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/trueterrabladeprojectile"));
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/trueshadowkatanaprojectile"));
        for (int i = 0; i < 4; i++) {
            evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/wing_" + i));
        }
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/wing_coregod"));
        evt.register(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/butterflyprojectile"));
    }

    public void onModelBake(ModelEvent.BakingCompleted evt) {
        for (int i = 0; i < firstFractalWeaponModels.length; i++) {
            firstFractalWeaponModels[i] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/sworddomain_" + i));
        }
        strengthenSlashModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/strengthenslash"));
        flamescionringModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/flamescionring"));
        influxwaverprojectileModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/influxwaverprojectile"));
        trueterrabladeprojectileModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/trueterrabladeprojectile"));
        trueshadowkatanaprojectileModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/trueshadowkatanaprojectile"));
        for (int i = 0; i < coregodWingsModel.length; i++) {
            coregodWingsModel[i] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/wing_" + i));
        }
        coregodModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/wing_coregod"));
        butterflyprojectileModel[0] = evt.getModels().get(ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "icon/butterflyprojectile"));
    }

    private static Material mainAtlas(String name) {
        return new Material(TextureAtlas.LOCATION_BLOCKS, prefix(name));
    }

}
