package com.chellrose.chellcraft.features.hat;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CommandHat {

    public static final int HELMET_ARMOR_INDEX = 39;
    public static final String COMMAND = "hat";

    public CommandHat() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
            CommandManager.literal(COMMAND).executes(context -> this.hat(context))));
    }

    public int hat(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFeedback(() -> Text.literal("You must be a player to use this command"), false);
            return -1;
        }
        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getStack(inventory.selectedSlot);
        ItemStack head = inventory.getStack(HELMET_ARMOR_INDEX);
        inventory.setStack(inventory.selectedSlot, head);
        inventory.setStack(HELMET_ARMOR_INDEX, mainHand);
        player.sendMessage(Text.literal("It's on your head now!").styled(style -> style.withItalic(true)));
        return 1;
    }
}