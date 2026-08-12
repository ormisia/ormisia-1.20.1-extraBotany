package com.meteor.extrabotany.common.blocks.tile;

import com.google.common.base.Suppliers;
import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.bauble.ItemNatureOrb;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TilePowerFrame extends SimpleInventoryBlockEntity {

    public static final int TRANSFER_SPEED = 1000;

    public TilePowerFrame(BlockPos pos, BlockState state) {
        super(ModTiles.POWER_FRAME.get(), pos, state);
    }

    private static final String[][] PATTERN_ADV = new String[][] {
            {
                    "P_____P",
                    "_______",
                    "_______",
                    "_______",
                    "_______",
                    "_______",
                    "P_____P"
            },
            {
                    "M_____M",
                    "_______",
                    "_______",
                    "___0___",
                    "_______",
                    "_______",
                    "M_____M"
            }
    };

    public static final Supplier<IMultiblock> MULTIBLOCK_ADV = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(
            PATTERN_ADV,
            'P', BotaniaBlocks.naturaPylon,
            '0', com.meteor.extrabotany.common.blocks.ModBlocks.powerframe.get(),
            'M', BotaniaBlocks.manaPool));

    public static final BlockPos[] POOL_LOCATIONS = {
            new BlockPos(3, 0, 3), new BlockPos(-3, 0, 3), new BlockPos(3, 0, -3), new BlockPos(-3, 0, -3)
    };

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean canPlaceItem(int slot, @Nonnull ItemStack stack) {
                return stack.getCapability(BotaniaForgeCapabilities.MANA_ITEM).isPresent() || stack.getItem() == ModItems.natureorb.get();
            }
        };
    }

    public boolean addItem(@Nullable Player player, ItemStack stack, @Nullable InteractionHand hand) {
        if (!stack.getCapability(BotaniaForgeCapabilities.MANA_ITEM).isPresent() && stack.getItem() != ModItems.natureorb.get())
            return false;

        boolean did = false;

        if (getItemHandler().getItem(0).isEmpty()) {
            did = true;
            ItemStack stackToAdd = stack.copy();
            stackToAdd.setCount(1);
            getItemHandler().setItem(0, stackToAdd);

            if (player == null || !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (did) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }

        return true;
    }

    public static void commonTick(Level world, BlockPos pos, BlockState state, TilePowerFrame self) {

        int redstoneSignal = 0;
        for (Direction dir : Direction.values()) {
            int redstoneSide = world.getSignal(pos.relative(dir), dir);
            redstoneSignal = Math.max(redstoneSignal, redstoneSide);
        }

        boolean transfering = false;
        int ritual = 0;

        if (MULTIBLOCK_ADV.get().validate(world, pos) != null) {
            ritual = 1;
        }

        int speed = TRANSFER_SPEED * (1 + ritual);

        ItemStack stack = self.getItemHandler().getItem(0);
        if (!stack.isEmpty()) {
            ManaItem item = stack.getCapability(BotaniaForgeCapabilities.MANA_ITEM).orElse(null);
            if (item != null) {
                if (world.getBlockEntity(pos.above()) instanceof ManaPoolBlockEntity) {
                    ManaPoolBlockEntity p = (ManaPoolBlockEntity) world.getBlockEntity(pos.above());

                    if (redstoneSignal == 0) {
                        int manaToGet = Math.min(speed, p.getCurrentMana());
                        int space = Math.max(0, item.getMaxMana() - item.getMana());
                        int current = Math.min(space, manaToGet);
                        if (!world.isClientSide) {
                            p.receiveMana(-current);
                            item.addMana(current);
                        }
                        if (current > 0)
                            transfering = true;
                    } else {
                        int manaToGet = Math.min(speed, item.getMana());
                        int space = Math.max(0, p.getMaxMana() - p.getCurrentMana());
                        int current = Math.min(space, manaToGet);
                        if (!world.isClientSide) {
                            p.receiveMana(current);
                            item.addMana(-current);
                        }
                        if (current > 0)
                            transfering = true;
                    }

                }
            } else if (stack.getItem() == ModItems.natureorb.get() && ritual > 0) {
                int xp = (int) Math.pow(4, ritual);
                ItemNatureOrb orb = (ItemNatureOrb) stack.getItem();
                if (!world.isClientSide)
                    orb.addXP(stack, xp);
                if (orb.getXP(stack) < orb.getMaxXP(stack))
                    transfering = true;

                for (BlockPos offset : POOL_LOCATIONS) {
                    BlockEntity tile = world.getBlockEntity(pos.offset(offset));
                    if (tile instanceof ManaPoolBlockEntity) {
                        ManaPoolBlockEntity pool = (ManaPoolBlockEntity) tile;
                        if (pool.getCurrentMana() >= 10) {
                            pool.receiveMana(-10);
                            orb.addXP(stack, 2);
                        }
                    }
                }
            }
        }

        if (world.isClientSide && ritual >= 1 && transfering) {
            Vec3 centerPos = new Vec3(pos.getX(), pos.getY() + 0.5, pos.getZ());
            for (BlockPos arr : POOL_LOCATIONS) {
                Vec3 pylonPos = new Vec3(pos.getX() + arr.getX(), pos.getY() + arr.getY() + 1.2F, pos.getZ() + arr.getZ());
                double worldTime = ClientTickHandler.ticksInGame;
                worldTime /= 5;

                float rad = 0.75F + (float) Math.random() * 0.05F;
                double xp = pylonPos.x + 0.5 + Math.cos(worldTime) * rad;
                double zp = pylonPos.z + 0.5 + Math.sin(worldTime) * rad;

                Vec3 partPos = new Vec3(xp, pylonPos.y, zp);
                Vec3 mot = centerPos.subtract(partPos).scale(0.04);

                float r = (float) Math.random() * 0.3F;
                float g = 0.75F + (float) Math.random() * 0.2F;
                float b = (float) Math.random() * 0.3F;

                WispParticleData data = WispParticleData.wisp(0.25F + (float) Math.random() * 0.1F, r, g, b, 1);
                world.addParticle(data, partPos.x, partPos.y, partPos.z, 0, -(-0.075F - (float) Math.random() * 0.015F), 0);
                WispParticleData data1 = WispParticleData.wisp(0.4F, r, g, b);
                world.addParticle(data1, partPos.x, partPos.y, partPos.z, (float) mot.x, (float) mot.y, (float) mot.z);
            }
        }
    }

}
