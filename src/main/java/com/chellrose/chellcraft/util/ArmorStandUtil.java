package com.chellrose.chellcraft.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandUtil {
    public static boolean armorStandHasItems(ArmorStand armorStand) {
        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (!armorStand.getItemBySlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
