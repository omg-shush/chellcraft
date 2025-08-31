package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntityDismountCallback {
    Event<EntityDismountCallback> EVENT = EventFactory.createArrayBacked(EntityDismountCallback.class,
    (listeners) -> (passenger, mount) -> {
        for (EntityDismountCallback listener : listeners) {
            ActionResult result = listener.onDismount(passenger, mount);
        
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onDismount(Entity passenger, Entity mount);
}
