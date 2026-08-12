package com.meteor.extrabotany.common.crafting;

import com.meteor.extrabotany.common.crafting.recipe.CocktailRecipe;
import com.meteor.extrabotany.common.crafting.recipe.GoldClothRecipe;
import com.meteor.extrabotany.common.crafting.recipe.HolyGrenadeRecipe;
import com.meteor.extrabotany.common.crafting.recipe.InfiniteWineChangeRecipe;
import com.meteor.extrabotany.common.crafting.recipe.InfiniteWineRecipe;
import com.meteor.extrabotany.common.crafting.recipe.LensPotionRecipe;
import com.meteor.extrabotany.common.crafting.recipe.RelicUpgradeRecipe;
import com.meteor.extrabotany.common.crafting.recipe.RelicUpgradeShapedRecipe;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LibMisc.MOD_ID);
    public static final RegistryObject<RecipeSerializer<GoldClothRecipe>> GOLDCLOTH = SERIALIZERS.register("goldcloth", () -> GoldClothRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<InfiniteWineChangeRecipe>> INFINITEWINE_CHANGE = SERIALIZERS.register("infinitewine_change", () -> InfiniteWineChangeRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<HolyGrenadeRecipe>> HOLYGRENADE = SERIALIZERS.register("holygrenade", () -> HolyGrenadeRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<InfiniteWineRecipe>> INFINITEWINE = SERIALIZERS.register("infinitewine", () -> InfiniteWineRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<CocktailRecipe>> COCKTAIL = SERIALIZERS.register("cocktail", () -> CocktailRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<LensPotionRecipe>> LENSPOTION = SERIALIZERS.register("lenspotion", () -> LensPotionRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<RelicUpgradeRecipe>> RELICUPGRADE = SERIALIZERS.register("relicupgrade", () -> RelicUpgradeRecipe.SERIALIZER);
    public static final RegistryObject<RecipeSerializer<RelicUpgradeShapedRecipe>> RELICUPGRADESHAPED = SERIALIZERS.register("relicupgradeshaped", () -> RelicUpgradeShapedRecipe.SERIALIZER);
}
