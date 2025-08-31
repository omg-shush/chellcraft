package com.chellrose.chellcraft.features.invis;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.ProjectileHitCallback;
import com.chellrose.chellcraft.util.ArmorStandUtil;
import com.chellrose.chellcraft.util.ArrowUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;

public class ListenerMakeInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerMakeInvis() {
        ProjectileHitCallback.EVENT.register(this::hit);
    }

    private ActionResult hit(EntityHitResult hit, PersistentProjectileEntity projectile) {
        if (ArrowUtil.isInvisArrow(projectile)) {
            Entity target = hit.getEntity();
            boolean applied = false;
            if (target instanceof ArmorStandEntity) {
                ArmorStandEntity armorStand = (ArmorStandEntity) target;
                if (!armorStand.isInvisible() && ArmorStandUtil.armorStandHasItems(armorStand)) {
                    armorStand.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    applied = true;
                }
            } else if (target instanceof ItemFrameEntity) {
                ItemFrameEntity itemFrame = (ItemFrameEntity) target;
                if (!itemFrame.isInvisible() && itemFrameHasItem(itemFrame)) {
                    itemFrame.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    applied = true;
                }
            }

            if (applied) {
                ArrowEntity arrow = (ArrowEntity) projectile;
                Entity source = arrow.getEffectCause();
                if (source instanceof ServerPlayerEntity) {
                    ServerPlayerEntity player = (ServerPlayerEntity) source;
                    player.playSoundToPlayer(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.5f, 1.0f);
                }
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    private boolean itemFrameHasItem(ItemFrameEntity itemFrame) {
        return !itemFrame.getHeldItemStack().isEmpty();
    }
}
