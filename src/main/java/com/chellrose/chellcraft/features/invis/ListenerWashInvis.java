package com.chellrose.chellcraft.features.invis;

import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.SplashWaterPotionCallback;

public class ListenerWashInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerWashInvis() {
        SplashWaterPotionCallback.EVENT.register(this::splashWaterWashInvis);
    }

    private InteractionResult splashWaterWashInvis(AbstractThrownPotion potion, ServerLevel world) {
        AABB box = potion.getBoundingBox().inflate(4.0, 2.0, 4.0);
        boolean success = false;
		for (Entity entity : world.getEntitiesOfClass(Entity.class, box, this.isInRange(potion).and(this::isWashable))) {
            success = true;
            if (entity.getType() == EntityType.ARMOR_STAND || entity.getType() == EntityType.ITEM_FRAME || entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                entity.setInvisible(false);
            } else if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.hasEffect(MobEffects.INVISIBILITY)) {
                    livingEntity.removeEffect(MobEffects.INVISIBILITY);
                    livingEntity.playSound(SoundEvents.PLAYER_SPLASH, 0.5f, 1.0f);
                }
            }
        }
        return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    private Predicate<Entity> isInRange(AbstractThrownPotion potion) {
        return entity -> potion.distanceToSqr(entity) < 16.0;
    }

    private boolean isWashable(Entity entity) {
        return entity instanceof ArmorStand || entity instanceof ItemFrame || entity instanceof LivingEntity;
    }
}
