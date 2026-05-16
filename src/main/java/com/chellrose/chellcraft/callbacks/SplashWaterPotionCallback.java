package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;

public interface SplashWaterPotionCallback {
    Event<SplashWaterPotionCallback> EVENT = EventFactory.createArrayBacked(SplashWaterPotionCallback.class,
    (listeners) -> (potion, world) -> {
        for (SplashWaterPotionCallback listener : listeners) {
            InteractionResult result = listener.onSplashWaterPotion(potion, world);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onSplashWaterPotion(AbstractThrownPotion potion, ServerLevel world);
}
