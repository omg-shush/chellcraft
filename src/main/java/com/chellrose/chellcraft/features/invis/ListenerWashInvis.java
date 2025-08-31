package com.chellrose.chellcraft.features.invis;

import java.util.function.Predicate;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.SplashWaterPotionCallback;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;

public class ListenerWashInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerWashInvis() {
        SplashWaterPotionCallback.EVENT.register(this::splashWaterWashInvis);
    }

    private ActionResult splashWaterWashInvis(PotionEntity potion, ServerWorld world) {
        Box box = potion.getBoundingBox().expand(4.0, 2.0, 4.0);
        boolean success = false;
		for (Entity entity : world.getEntitiesByClass(Entity.class, box, this.isInRange(potion).and(this::isWashable))) {
            success = true;
            if (entity.getType() == EntityType.ARMOR_STAND || entity.getType() == EntityType.ITEM_FRAME || entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                entity.setInvisible(false);
            } else if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                    livingEntity.removeStatusEffect(StatusEffects.INVISIBILITY);
                    livingEntity.playSound(SoundEvents.ENTITY_PLAYER_SPLASH, 0.5f, 1.0f);
                }
            }
        }
        return success ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    private Predicate<Entity> isInRange(PotionEntity potion) {
        return entity -> potion.squaredDistanceTo(entity) < 16.0;
    }

    private boolean isWashable(Entity entity) {
        return entity instanceof ArmorStandEntity || entity instanceof ItemFrameEntity || entity instanceof LivingEntity;
    }
}
