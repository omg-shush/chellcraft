package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ArmorStandRemoveItemCallback {
    Event<ArmorStandRemoveItemCallback> EVENT = EventFactory.createArrayBacked(ArmorStandRemoveItemCallback.class,
    (listeners) -> (armorStand, player) -> {
        for (ArmorStandRemoveItemCallback listener : listeners) {
            ActionResult result = listener.onArmorStandUnequip(armorStand, player);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onArmorStandUnequip(ArmorStandEntity armorStand, PlayerEntity player);
}
