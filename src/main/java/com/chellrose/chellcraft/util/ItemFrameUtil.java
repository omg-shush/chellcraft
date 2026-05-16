package com.chellrose.chellcraft.util;

import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemFrameUtil {
    public static boolean itemFrameHasItem(ItemFrame itemFrame) {
        return !itemFrame.getItem().isEmpty();
    }

    public static ItemStack getAsItemStack(ItemFrame itemFrame) {
        if (itemFrame instanceof GlowItemFrame) {
            return new ItemStack(Items.GLOW_ITEM_FRAME);
        }
        return new ItemStack(Items.ITEM_FRAME);
    }
}
