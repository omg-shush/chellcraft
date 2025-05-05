package com.chellrose.chellcraft.features.invis;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.util.ProjectileHitCallback;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;

public class ListenerMakeInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerMakeInvis() {
        ProjectileHitCallback.EVENT.register((hit, projectile) -> hit(hit, projectile));
    }

    private ActionResult hit(EntityHitResult hit, ProjectileEntity projectile) {
        if (isInvisArrow(projectile)) {
            Entity target = hit.getEntity();
            if (target instanceof ArmorStandEntity) {
                ArmorStandEntity armorStand = (ArmorStandEntity) target;
                if (!armorStand.isInvisible() && armorStandHasItems(armorStand)) {
                    armorStand.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    return ActionResult.FAIL;
                }
            } else if (target instanceof ItemFrameEntity) {
                ItemFrameEntity itemFrame = (ItemFrameEntity) target;
                if (!itemFrame.isInvisible() && itemFrameHasItem(itemFrame)) {
                    itemFrame.setInvisible(true);
                    projectile.remove(RemovalReason.DISCARDED);
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }

    private boolean isInvisArrow(ProjectileEntity projectile) {
        if (projectile instanceof ArrowEntity) {
            ArrowEntity arrow = (ArrowEntity) projectile;
            PotionContentsComponent potion = arrow.getItemStack().get(DataComponentTypes.POTION_CONTENTS);
            if (potion != null) {
                return potion.matches(Registries.POTION.getEntry(Identifier.ofVanilla("invisibility")).get());
            }
        }
        return false;
    }

    private boolean armorStandHasItems(ArmorStandEntity armorStand) {
        for (ItemStack i : armorStand.getArmorItems()) {
            if (!i.isEmpty()) {
                return true;
            }
        }
        if (!armorStand.getMainHandStack().isEmpty() || !armorStand.getOffHandStack().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean itemFrameHasItem(ItemFrameEntity itemFrame) {
        return !itemFrame.getHeldItemStack().isEmpty();
    }
}
