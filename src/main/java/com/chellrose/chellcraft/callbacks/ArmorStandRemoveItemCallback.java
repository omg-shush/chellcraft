package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

public interface ArmorStandRemoveItemCallback {
    Event<ArmorStandRemoveItemCallback> EVENT = EventFactory.createArrayBacked(ArmorStandRemoveItemCallback.class,
    (listeners) -> (armorStand, player) -> {
        for (ArmorStandRemoveItemCallback listener : listeners) {
            InteractionResult result = listener.onArmorStandUnequip(armorStand, player);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onArmorStandUnequip(ArmorStand armorStand, Player player);
}
