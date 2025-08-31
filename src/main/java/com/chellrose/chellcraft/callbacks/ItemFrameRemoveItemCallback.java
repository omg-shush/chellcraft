package com.chellrose.chellcraft.callbacks;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ItemFrameRemoveItemCallback {
    Event<ItemFrameRemoveItemCallback> EVENT = EventFactory.createArrayBacked(ItemFrameRemoveItemCallback.class,
    (listeners) -> (itemFrame, player) -> {
        for (ItemFrameRemoveItemCallback listener : listeners) {
            ActionResult result = listener.onItemFrameUnequip(itemFrame, player);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onItemFrameUnequip(ItemFrameEntity itemFrame, @Nullable PlayerEntity player);
}
