package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;

public interface EntityDismountCallback {
    Event<EntityDismountCallback> EVENT = EventFactory.createArrayBacked(EntityDismountCallback.class,
    (listeners) -> (passenger, mount) -> {
        for (EntityDismountCallback listener : listeners) {
            InteractionResult result = listener.onDismount(passenger, mount);
        
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onDismount(Entity passenger, Entity mount);
}
