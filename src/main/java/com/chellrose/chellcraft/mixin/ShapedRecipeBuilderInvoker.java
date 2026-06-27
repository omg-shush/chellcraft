package com.chellrose.chellcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;

@Mixin(ShapedRecipeBuilder.class)
public interface ShapedRecipeBuilderInvoker {
    @Invoker("<init>")
    static ShapedRecipeBuilder chellcraft$shapedRecipeBuilder(final HolderGetter<Item> items, final RecipeCategory category, final ItemStackTemplate result) {
        throw new UnsupportedOperationException("This is a mixin invoker method and should not be called directly.");
    }
}
