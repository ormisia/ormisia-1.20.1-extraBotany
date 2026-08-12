package com.meteor.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import vazkii.botania.api.item.Relic;
import vazkii.botania.xplat.XplatAbstractions;

import javax.annotation.Nonnull;

public class RelicUpgradeRecipe implements CraftingRecipe {

    private final ShapelessRecipe compose;

    public RelicUpgradeRecipe(ShapelessRecipe compose) {
        this.compose = compose;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inv, @Nonnull Level world) {
        return compose.matches(inv, world);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer inv, @Nonnull RegistryAccess registryAccess) {
        ItemStack out = compose.assemble(inv, registryAccess);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && XplatAbstractions.INSTANCE.findRelic(stack) != null) {
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(stack), out);
                Relic relic = XplatAbstractions.INSTANCE.findRelic(stack);
                Relic outRelic = XplatAbstractions.INSTANCE.findRelic(out);
                if (outRelic != null && relic.getSoulbindUUID() != null) {
                    outRelic.bindToUUID(relic.getSoulbindUUID());
                }
                break;
            }
        }
        return out;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return compose.canCraftInDimensions(width, height);
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull RegistryAccess registryAccess) {
        return compose.getResultItem(registryAccess);
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return compose.getIngredients();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return compose.getId();
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public CraftingBookCategory category() {
        return compose.category();
    }

    public static final RecipeSerializer<RelicUpgradeRecipe> SERIALIZER = new Serializer();

    private static class Serializer implements RecipeSerializer<RelicUpgradeRecipe> {
        @Override
        public RelicUpgradeRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            return new RelicUpgradeRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromJson(recipeId, json));
        }

        @Override
        public RelicUpgradeRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            return new RelicUpgradeRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull RelicUpgradeRecipe recipe) {
            RecipeSerializer.SHAPELESS_RECIPE.toNetwork(buffer, recipe.compose);
        }
    };
}
