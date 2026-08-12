package com.meteor.extrabotany.common.crafting.recipe;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.brew.ItemBrewBase;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import vazkii.botania.api.brew.BrewItem;

public class LensPotionRecipe extends CustomRecipe {

    public static final SimpleCraftingRecipeSerializer<LensPotionRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(LensPotionRecipe::new);

    public LensPotionRecipe(ResourceLocation idIn, CraftingBookCategory category) {
        super(idIn, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        boolean foundBrew = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.cocktail.get() && !foundBrew) {
                    foundBrew = true;
                } else if (!foundItem) {
                    if (stack.getItem() == ModItems.lenspotion.get()) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundBrew && foundItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack brewstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.cocktail.get() && brewstack.isEmpty()) {
                    brewstack = stack;
                }
            }
        }

        BrewItem brew = (BrewItem) brewstack.getItem();
        ItemStack lens = new ItemStack(ModItems.lenspotion.get());
        ItemBrewBase.setBrew(lens, brew.getBrew(brewstack));

        return lens;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
