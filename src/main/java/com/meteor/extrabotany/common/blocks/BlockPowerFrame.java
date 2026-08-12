package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.tile.ModTiles;
import com.meteor.extrabotany.common.blocks.tile.TilePowerFrame;
import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.block.BotaniaBlock;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;
import vazkii.botania.common.helper.InventoryHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPowerFrame extends BotaniaBlock implements EntityBlock {

    public BlockPowerFrame(Properties builder) {
        super(builder);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        TilePowerFrame frame = (TilePowerFrame) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            InventoryHelper.withdrawFromInventory(frame, player);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(frame);
            return InteractionResult.SUCCESS;
        } else if (!stack.isEmpty() && stack.getCapability(BotaniaForgeCapabilities.MANA_ITEM).isPresent() || stack.getItem() == ModItems.natureorb.get()) {
            boolean result = frame.addItem(player, stack, hand);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(frame);
            return result ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TilePowerFrame(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModTiles.POWER_FRAME.get(), TilePowerFrame::commonTick);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof SimpleInventoryBlockEntity) {
                Containers.dropContents(world, pos, ((SimpleInventoryBlockEntity) te).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

}
