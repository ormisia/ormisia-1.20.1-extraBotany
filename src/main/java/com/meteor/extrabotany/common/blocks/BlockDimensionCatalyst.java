package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import vazkii.botania.api.mana.PoolOverlayProvider;

public class BlockDimensionCatalyst extends Block implements PoolOverlayProvider {

    public BlockDimensionCatalyst(Properties builder) {
        super(builder);
    }

    @Override
    public ResourceLocation getIcon(Level world, BlockPos pos) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "block/dimensioncatalyst");
    }
}
