package com.chellrose.chellcraft.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.alchemy.PotionContents;

public class ArrowUtil {
    public static boolean isInvisArrow(AbstractArrow projectile) {
        if (projectile instanceof Arrow) {
            Arrow arrow = (Arrow) projectile;
            PotionContents potion = arrow.getPickupItemStackOrigin().get(DataComponents.POTION_CONTENTS);
            if (potion != null) {
                return potion.is(BuiltInRegistries.POTION.get(Identifier.withDefaultNamespace("invisibility")).get());
            }
        }
        return false;
    }
}
