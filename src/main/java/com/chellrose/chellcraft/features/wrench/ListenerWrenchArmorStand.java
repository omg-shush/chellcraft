package com.chellrose.chellcraft.features.wrench;

import java.util.Objects;

import org.jspecify.annotations.NonNull;

import com.chellrose.chellcraft.features.armorstand.ArmorStandPose;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ListenerWrenchArmorStand {
    public ListenerWrenchArmorStand() {
        UseEntityCallback.EVENT.register(this::entity);
    }

    private @NonNull InteractionResult entity(Player player, Level level, @NonNull InteractionHand hand, Entity entity, EntityHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        boolean mainHand = hand.asEquipmentSlot().equals(EquipmentSlot.MAINHAND);
        boolean isWrench = WrenchItem.isWrench(stack);
        boolean isArmorStand = entity.getType().equals(EntityTypes.ARMOR_STAND);
        boolean isSneaking = player.isShiftKeyDown();
        if (mainHand && isWrench && isArmorStand && isSneaking) {
            // Cycle to next armor stand pose
            ArmorStand armorStand = (ArmorStand) entity;
            ArmorStandPose currentPose = new ArmorStandPose(armorStand);
            int nextIndex = currentPose.index() + 1;
            ArmorStandPose nextPose = ArmorStandPose.fromIndex(nextIndex);
            nextPose.apply(armorStand);
            // Can't damage armor stand in creative mode without immediately breaking
            if (player.gameMode() != null && !Objects.requireNonNull(player.gameMode()).isCreative()) {
                armorStand.hurtServer((ServerLevel) level, level.damageSources().playerAttack(player), 0.0f);
                armorStand.lastHit = level.getGameTime() - 100L; // Reset the last hit time to prevent armor stand being broken
            }
            armorStand.playSound(SoundEvents.ARMOR_STAND_HIT);

            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
