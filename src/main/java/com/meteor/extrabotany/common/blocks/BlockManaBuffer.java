package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.tile.ModTiles;
import com.meteor.extrabotany.common.blocks.tile.TileManaBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.BotaniaBlock;

import javax.annotation.Nullable;

public class BlockManaBuffer extends BotaniaBlock implements EntityBlock {

    public BlockManaBuffer(Properties builder) {
        super(builder);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileManaBuffer(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, ModTiles.MANA_BUFFER.get(), TileManaBuffer::serverTick);
    }
}
