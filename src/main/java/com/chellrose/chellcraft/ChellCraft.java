package com.chellrose.chellcraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Objects;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chellrose.chellcraft.features.armorstand.ListenerArmorStand;
import com.chellrose.chellcraft.features.hat.CommandHat;
import com.chellrose.chellcraft.features.help.CommandHelp;
import com.chellrose.chellcraft.features.invis.ListenerMakeInvis;
import com.chellrose.chellcraft.features.invis.ListenerRemoveItemFromInvis;
import com.chellrose.chellcraft.features.invis.ListenerWashInvis;
import com.chellrose.chellcraft.features.sit.ListenerSit;
import com.chellrose.chellcraft.features.wrench.ListenerWrenchArmorStand;
import com.chellrose.chellcraft.features.wrench.ListenerWrenchBlock;

public class ChellCraft implements ModInitializer {
	public static final String MOD_ID = "chellcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing ChellCraft...");

		// Register the hat command
		new CommandHat();

		// Register the help command
		new CommandHelp();

		// Register the sit feature
		new ListenerSit();

		// Register the invis static entities feature
		new ListenerMakeInvis();
		new ListenerRemoveItemFromInvis();
		new ListenerWashInvis();

		// Register the armor stand modification feature
		new ListenerArmorStand();

		// Register the wrench feature
		@NonNull ModContainer thisContainer = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MOD_ID).get());
		ResourceLoader.registerBuiltinPack(ChellCraftDataGenerator.WRENCH_RECIPE_PACK_ID, thisContainer, PackActivationType.ALWAYS_ENABLED);
		new ListenerWrenchArmorStand();
		new ListenerWrenchBlock();

		LOGGER.info("Ready for liftoff!");
	}
}
