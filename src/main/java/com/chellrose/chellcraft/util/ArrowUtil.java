package com.chellrose.chellcraft.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ArrowUtil {
    public static boolean isInvisArrow(PersistentProjectileEntity projectile) {
        if (projectile instanceof ArrowEntity) {
            ArrowEntity arrow = (ArrowEntity) projectile;
            PotionContentsComponent potion = arrow.getItemStack().get(DataComponentTypes.POTION_CONTENTS);
            if (potion != null) {
                return potion.matches(Registries.POTION.getEntry(Identifier.ofVanilla("invisibility")).get());
            }
        }
        return false;
    }
}
