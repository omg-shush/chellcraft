package com.chellrose.chellcraft.features.invis;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.ProjectileHitCallback;
import com.chellrose.chellcraft.util.ArmorStandUtil;
import com.chellrose.chellcraft.util.ArrowUtil;
import com.chellrose.chellcraft.util.ItemFrameUtil;
import com.chellrose.chellcraft.util.PlayerSoundUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.phys.EntityHitResult;

public class ListenerMakeInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerMakeInvis() {
        ProjectileHitCallback.EVENT.register(this::hit);
    }

    private InteractionResult hit(EntityHitResult hit, AbstractArrow projectile) {
        if (ArrowUtil.isInvisArrow(projectile)) {
            Entity target = hit.getEntity();
            boolean applied = false;
            if (target instanceof ArmorStand) {
                ArmorStand armorStand = (ArmorStand) target;
                if (!armorStand.isInvisible() && ArmorStandUtil.armorStandHasItems(armorStand)) {
                    armorStand.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    applied = true;
                }
            } else if (target instanceof ItemFrame) {
                ItemFrame itemFrame = (ItemFrame) target;
                if (!itemFrame.isInvisible() && ItemFrameUtil.itemFrameHasItem(itemFrame)) {
                    itemFrame.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    applied = true;
                }
            }

            if (applied) {
                Arrow arrow = (Arrow) projectile;
                Entity source = arrow.getEffectSource();
                if (source instanceof ServerPlayer) {
                    ServerPlayer player = (ServerPlayer) source;
                    PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.ARROW_HIT_PLAYER, SoundSource.BLOCKS, 0.5f, 1.0f);
                }
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }
}
