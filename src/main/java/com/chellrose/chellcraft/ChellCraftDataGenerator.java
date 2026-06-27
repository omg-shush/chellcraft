package com.chellrose.chellcraft;

import java.util.Objects;

import org.jspecify.annotations.NonNull;
import com.chellrose.chellcraft.features.wrench.WrenchRecipeProvider;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;
import net.minecraft.resources.Identifier;

public class ChellCraftDataGenerator implements DataGeneratorEntrypoint {
	public static final @NonNull Identifier WRENCH_RECIPE_PACK_ID = Objects.requireNonNull(Identifier.fromNamespaceAndPath(ChellCraft.MOD_ID, "wrench"));

	@Override
	public void onInitializeDataGenerator(@NonNull FabricDataGenerator fabricDataGenerator) {
		Pack wrench = fabricDataGenerator.createBuiltinResourcePack(WRENCH_RECIPE_PACK_ID);
		wrench.addProvider(WrenchRecipeProvider::new);
	}
}
