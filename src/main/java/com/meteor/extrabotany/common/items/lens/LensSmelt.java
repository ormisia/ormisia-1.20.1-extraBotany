package com.meteor.extrabotany.common.items.lens;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.lens.Lens;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.item.lens.BoreLens;
import vazkii.botania.xplat.BotaniaConfig;

public class LensSmelt extends Lens {

    @Override
    public boolean collideBurst(ManaBurst burst, HitResult rtr, boolean isManaBlock, boolean dead, ItemStack stack) {
        ThrowableProjectile entity = burst.entity();
        Level world = entity.level();

        if (world.isClientSide || rtr.getType() != HitResult.Type.BLOCK) {
            return false;
        }

        BlockPos collidePos = ((BlockHitResult) rtr).getBlockPos();
        BlockState state = world.getBlockState(collidePos);
        Block block = state.getBlock();

        ItemStack composite = ((LensItem) stack.getItem()).getCompositeLens(stack);
        boolean warp = !composite.isEmpty() && composite.is(BotaniaItems.lensWarp);

        int harvestLevel = BotaniaConfig.common().harvestLevelBore();
        BlockEntity tile = world.getBlockEntity(collidePos);

        float hardness = state.getDestroySpeed(world, collidePos);
        int mana = burst.getMana();

        BlockPos source = burst.getBurstSourceBlockPos();
        if (!source.equals(collidePos)
                && !(tile instanceof ManaReceiver)
                && canHarvest(harvestLevel, state)
                && hardness != -1 && hardness < 50F
                && (burst.isFake() || mana >= 24)) {
            if (!burst.hasAlreadyCollidedAt(collidePos)) {
                if (!burst.isFake()) {

                    Recipe<?> irecipe = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(new ItemStack(block)), world).orElse(null);

                    if(irecipe != null && !irecipe.getResultItem(world.registryAccess()).isEmpty()) {

                        world.destroyBlock(collidePos, false);
                        if (BotaniaConfig.common().blockBreakParticles()) {
                            world.levelEvent(2001, collidePos, Block.getId(state));
                        }

                        boolean offBounds = source.getY() < 0;
                        boolean doWarp = warp && !offBounds;
                        BlockPos dropCoord = doWarp ? source : collidePos;
                        Block.popResource(world, dropCoord, irecipe.getResultItem(world.registryAccess()).copy());

                        burst.setMana(mana - 40);
                    }
                }
            }

            dead = false;
        }

        return dead;
    }

    private static boolean canHarvest(int harvestLevel, BlockState state) {
        // 1.20.1 removed Block#getHarvestLevel; use Botania's bore lens tool check,
        // but allow blocks that don't require a correct tool (matches old "harvest level 0" behaviour).
        return !state.requiresCorrectToolForDrops() || BoreLens.canHarvest(harvestLevel, state);
    }

}
