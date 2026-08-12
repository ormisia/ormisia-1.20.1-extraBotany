package com.meteor.extrabotany.common.items.lens;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.recipe.ManaInfusionRecipe;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.item.lens.Lens;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LensMana extends Lens {

    @Override
    public void apply(ItemStack stack, BurstProperties props) {
        props.maxMana = 1000;
        props.motionModifier *= 0.5F;
        props.manaLossPerTick *= 2F;
    }

    @Override
    public void updateBurst(ManaBurst burst, ItemStack stack) {
        ThrowableProjectile entity = burst.entity();
        if (entity.level().isClientSide)
            return;
        int mana = burst.getMana();
        BlockState state = entity.level().getBlockState(burst.getBurstSourceBlockPos().offset(0, -1, 0));
        AABB axis = new AABB(entity.getX(), entity.getY(), entity.getZ(), entity.xOld,
                entity.yOld, entity.zOld).inflate(1);
        List<ItemEntity> entities = entity.level().getEntitiesOfClass(ItemEntity.class, axis);
        if(!burst.isFake())
            for (ItemEntity items : entities) {
                if (items.hasPickUpDelay())
                    continue;
                ItemStack itemstack = items.getItem();
                ManaInfusionRecipe recipe = getMatchingRecipe(entity.level(), itemstack, state);
                if (recipe != null) {
                    int manaToConsume = recipe.getManaToConsume();
                    if (mana >= manaToConsume) {
                        burst.setMana((int) (mana - manaToConsume));
                        itemstack.shrink(1);

                        ItemStack output = recipe.getRecipeOutput(entity.level().registryAccess(), itemstack).copy();
                        ItemEntity outputItem = new ItemEntity(entity.level(), items.getX(), items.getY()+0.5, items.getZ() + 0.5, output);
                        outputItem.setPickUpDelay(50);
                        entity.level().addFreshEntity(outputItem);
                    }
                }
            }
    }

    public static List<ManaInfusionRecipe> manaInfusionRecipes(Level world) {
        return BotaniaRecipeTypes.getRecipes(world, BotaniaRecipeTypes.MANA_INFUSION_TYPE).values().stream()
                .filter(r -> r instanceof ManaInfusionRecipe)
                .map(r -> (ManaInfusionRecipe) r)
                .collect(Collectors.toList());
    }

    public ManaInfusionRecipe getMatchingRecipe(Level world, @Nonnull ItemStack stack, @Nonnull BlockState state) {
        List<ManaInfusionRecipe> matchingNonCatRecipes = new ArrayList<>();
        List<ManaInfusionRecipe> matchingCatRecipes = new ArrayList<>();

        for (ManaInfusionRecipe recipe : manaInfusionRecipes(world)) {
            if (recipe.matches(stack)) {
                if (recipe.getRecipeCatalyst() == null) {
                    matchingNonCatRecipes.add(recipe);
                } else if (recipe.getRecipeCatalyst().test(state)) {
                    matchingCatRecipes.add(recipe);
                }
            }
        }

        // Recipes with matching catalyst take priority above recipes with no catalyst specified
        return !matchingCatRecipes.isEmpty() ? matchingCatRecipes.get(0) : !matchingNonCatRecipes.isEmpty() ? matchingNonCatRecipes.get(0) : null;
    }
}
