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
import vazkii.botania.api.item.Relic;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.UUID;

public class InfiniteWineChangeRecipe extends CustomRecipe {

    public static final SimpleCraftingRecipeSerializer<InfiniteWineChangeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(InfiniteWineChangeRecipe::new);

    public InfiniteWineChangeRecipe(ResourceLocation idIn, CraftingBookCategory category) {
        super(idIn, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        boolean foundInfiniteWine = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.infinitewine.get() && !foundInfiniteWine) {
                    foundInfiniteWine = true;
                } else if (!foundItem) {
                    if (stack.getItem() == ModItems.cocktail.get()) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundInfiniteWine && foundItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack item = ItemStack.EMPTY;
        ItemStack cocktail = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.infinitewine.get() && item.isEmpty()) {
                    item = stack;
                } else if (cocktail.isEmpty()) {
                    if (stack.getItem() == ModItems.cocktail.get()) {
                        cocktail = stack;
                    }
                }
            }
        }

        Relic relic = XplatAbstractions.INSTANCE.findRelic(item);
        ItemStack copy = item.copy();
        Relic copyRelic = XplatAbstractions.INSTANCE.findRelic(copy);
        if (relic != null && copyRelic != null) {
            UUID soulbind = relic.getSoulbindUUID();
            if (soulbind != null) {
                copyRelic.bindToUUID(soulbind);
            }
        }

        BrewItem brew = (BrewItem) cocktail.getItem();
        ItemBrewBase.setBrew(copy, brew.getBrew(cocktail));

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
