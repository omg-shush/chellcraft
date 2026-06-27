package com.chellrose.chellcraft.features.wrench;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.mixin.ShapedRecipeBuilderInvoker;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public class WrenchRecipeProvider extends FabricRecipeProvider {
    public static final String WRENCH_RECIPE_PROVIDER = "ChellCraftWrenchRecipeProvider";

    public WrenchRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public @NonNull String getName() {
        return WRENCH_RECIPE_PROVIDER;
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new WrenchRecipeGenerator(registryLookup, exporter);
    }

    public class WrenchRecipeGenerator extends RecipeProvider {
        public static final @NonNull Identifier WRENCH_RECIPE_KEY = Objects.requireNonNull(Identifier.fromNamespaceAndPath(ChellCraft.MOD_ID, "wrench"));

        protected WrenchRecipeGenerator(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
            super(registryLookup, exporter);
        }

        @Override
        public void buildRecipes() {
            ShapedRecipeBuilderInvoker.chellcraft$shapedRecipeBuilder(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, WrenchItem.getTemplate())
                .pattern(" c")
                .pattern("r ")
                .define('c', Items.END_CRYSTAL)
                .define('r', Items.END_ROD)
                .group("wrenches")
                .unlockedBy(RecipeProvider.getHasName(Items.END_CRYSTAL), this.has(Items.END_CRYSTAL))
                .save(this.output, ResourceKey.create(Registries.RECIPE, WRENCH_RECIPE_KEY));
        }
    }
}
