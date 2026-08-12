package com.meteor.extrabotany.common.crafting.recipe;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.xplat.XplatAbstractions;

public class GoldClothRecipe extends CustomRecipe {

    public static final SimpleCraftingRecipeSerializer<GoldClothRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(GoldClothRecipe::new);

    public GoldClothRecipe(ResourceLocation idIn, CraftingBookCategory category) {
        super(idIn, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        boolean foundGoldCloth = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.goldcloth.get() && !foundGoldCloth) {
                    foundGoldCloth = true;
                } else if (!foundItem) {
                    if (XplatAbstractions.INSTANCE.findRelic(stack) != null) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundGoldCloth && foundItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack item = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (XplatAbstractions.INSTANCE.findRelic(stack) != null && item.isEmpty()) {
                    item = stack;
                }
            }
        }

        ItemStack copy = item.copy();
        ItemNBTHelper.removeEntry(copy, "soulbindUUID");
        return copy;
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
