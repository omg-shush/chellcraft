package com.chellrose.chellcraft.features.help;

import java.net.URI;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandHelp {

    public static final String COMMAND = "chellcraft";

    public CommandHelp() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            CommandManager.literal(COMMAND).executes(context -> this.help(context))));
    }

    public int help(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.literal("[Click for ChellCraft documentation]").styled(style -> style
            .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://github.com/omg-shush/chellcraft/blob/main/README.md")))
            .withUnderline(true)
            .withColor(Formatting.LIGHT_PURPLE)));
        return 0;
    }
}