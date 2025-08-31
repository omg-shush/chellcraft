package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface SplashWaterPotionCallback {
    Event<SplashWaterPotionCallback> EVENT = EventFactory.createArrayBacked(SplashWaterPotionCallback.class,
    (listeners) -> (potion, world) -> {
        for (SplashWaterPotionCallback listener : listeners) {
            ActionResult result = listener.onSplashWaterPotion(potion, world);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onSplashWaterPotion(PotionEntity potion, ServerWorld world);
}
