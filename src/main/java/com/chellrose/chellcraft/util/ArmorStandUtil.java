package com.chellrose.chellcraft.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class ArmorStandUtil {
    public static boolean armorStandHasItems(ArmorStandEntity armorStand) {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (!armorStand.getEquippedStack(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
