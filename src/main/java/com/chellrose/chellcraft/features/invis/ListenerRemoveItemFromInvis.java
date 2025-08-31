package com.chellrose.chellcraft.features.invis;

import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.ArmorStandRemoveItemCallback;
import com.chellrose.chellcraft.util.ArmorStandUtil;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class ListenerRemoveItemFromInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerRemoveItemFromInvis() {
        ArmorStandRemoveItemCallback.EVENT.register(this::armorStandRemoveItem);
    }

    private ActionResult armorStandRemoveItem(ArmorStandEntity armorStand, PlayerEntity player) {
        if (armorStand.isInvisible() && !ArmorStandUtil.armorStandHasItems(armorStand)) {
            armorStand.remove(RemovalReason.DISCARDED);
            ItemStack itemStack = new ItemStack(Items.ARMOR_STAND);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, armorStand.getCustomName());
            Block.dropStack(armorStand.getWorld(), armorStand.getBlockPos(), itemStack);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
