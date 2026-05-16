package com.chellrose.chellcraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;

public interface ProjectileHitCallback {
    Event<ProjectileHitCallback> EVENT = EventFactory.createArrayBacked(ProjectileHitCallback.class,
    (listeners) -> (projectile, target) -> {
        for (ProjectileHitCallback listener : listeners) {
            InteractionResult result = listener.onEntityHit(projectile, target);
        
            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return InteractionResult.PASS;
    });

    InteractionResult onEntityHit(EntityHitResult hit, AbstractArrow projectile);
}
