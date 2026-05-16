package com.chellrose.chellcraft.features.hat;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class CommandHat {

    public static final int HELMET_ARMOR_INDEX = 39;
    public static final String COMMAND = "hat";

    public CommandHat() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            Commands.literal(COMMAND).executes(context -> this.hat(context))));
    }

    public int hat(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendSuccess(() -> Component.literal("You must be a player to use this command"), false);
            return -1;
        }
        Inventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItem(inventory.getSelectedSlot());
        ItemStack head = inventory.getItem(HELMET_ARMOR_INDEX);
        inventory.setItem(inventory.getSelectedSlot(), head);
        inventory.setItem(HELMET_ARMOR_INDEX, mainHand);
        player.sendSystemMessage(Component.literal("It's on your head now!").withStyle(style -> style.withItalic(true)));
        return 1;
    }
}