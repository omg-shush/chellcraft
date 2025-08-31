package com.chellrose.chellcraft.util;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;

public interface ProjectileHitCallback {

    Event<ProjectileHitCallback> EVENT = EventFactory.createArrayBacked(ProjectileHitCallback.class,
    (listeners) -> (projectile, target) -> {
        for (ProjectileHitCallback listener : listeners) {
            ActionResult result = listener.onEntityHit(projectile, target);
        
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onEntityHit(EntityHitResult hit, PersistentProjectileEntity projectile);

}
