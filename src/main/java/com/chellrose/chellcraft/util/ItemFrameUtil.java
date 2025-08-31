package com.chellrose.chellcraft.util;

import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemFrameUtil {
    public static boolean itemFrameHasItem(ItemFrameEntity itemFrame) {
        return !itemFrame.getHeldItemStack().isEmpty();
    }

    public static ItemStack getAsItemStack(ItemFrameEntity itemFrame) {
        if (itemFrame instanceof GlowItemFrameEntity) {
            return new ItemStack(Items.GLOW_ITEM_FRAME);
        }
        return new ItemStack(Items.ITEM_FRAME);
    }
}
