package com.chellrose.chellcraft.callbacks;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;

public interface ItemFrameRemoveItemCallback {
    Event<ItemFrameRemoveItemCallback> EVENT = EventFactory.createArrayBacked(ItemFrameRemoveItemCallback.class,
    (listeners) -> (itemFrame, player) -> {
        for (ItemFrameRemoveItemCallback listener : listeners) {
            InteractionResult result = listener.onItemFrameUnequip(itemFrame, player);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onItemFrameUnequip(ItemFrame itemFrame, @Nullable Player player);
}
