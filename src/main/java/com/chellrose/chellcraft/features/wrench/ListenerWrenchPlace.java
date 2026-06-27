package com.chellrose.chellcraft.features.wrench;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ListenerWrenchPlace {
    public ListenerWrenchPlace() {
        UseBlockCallback.EVENT.register(this::place);
    }

    private @NonNull InteractionResult place(Player player, Level level, @NonNull InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (WrenchItem.isWrench(stack)) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}
