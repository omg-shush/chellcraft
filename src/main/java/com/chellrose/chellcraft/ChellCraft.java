package com.chellrose.chellcraft;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chellrose.chellcraft.features.hat.CommandHat;
import com.chellrose.chellcraft.features.help.CommandHelp;
import com.chellrose.chellcraft.features.sit.ListenerSit;

public class ChellCraft implements ModInitializer {
	public static final String MOD_ID = "chellcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		// Register the hat command
		new CommandHat();

		// Register the help command
		new CommandHelp();

		// Register the sit feature
		new ListenerSit();
	}
}