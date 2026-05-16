package com.chellrose.chellcraft.features.help;

import java.net.URI;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;

public class CommandHelp {

    public static final String COMMAND = "chellcraft";

    public CommandHelp() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            Commands.literal(COMMAND).executes(context -> this.help(context))));
    }

    public int help(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSystemMessage(Component.literal("[Click for ChellCraft documentation]").withStyle(style -> style
            .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://github.com/omg-shush/chellcraft/blob/main/README.md")))
            .withUnderlined(true)
            .withColor(ChatFormatting.LIGHT_PURPLE)));
        return 0;
    }
}