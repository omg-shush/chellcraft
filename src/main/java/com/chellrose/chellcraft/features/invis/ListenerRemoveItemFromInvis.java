package com.chellrose.chellcraft.features.invis;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.ArmorStandRemoveItemCallback;
import com.chellrose.chellcraft.callbacks.ItemFrameRemoveItemCallback;
import com.chellrose.chellcraft.util.ArmorStandUtil;
import com.chellrose.chellcraft.util.ItemFrameUtil;
import com.chellrose.chellcraft.util.PlayerSoundUtil;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class ListenerRemoveItemFromInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerRemoveItemFromInvis() {
        ArmorStandRemoveItemCallback.EVENT.register(this::armorStandRemoveItem);
        ItemFrameRemoveItemCallback.EVENT.register(this::itemFrameRemoveItem);
    }

    private ActionResult armorStandRemoveItem(ArmorStandEntity armorStand, PlayerEntity player) {
        if (armorStand.isInvisible() && !ArmorStandUtil.armorStandHasItems(armorStand)) {
            armorStand.remove(RemovalReason.DISCARDED);
            ItemStack itemStack = new ItemStack(Items.ARMOR_STAND);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, armorStand.getCustomName());
            Block.dropStack(armorStand.getEntityWorld(), armorStand.getBlockPos(), itemStack);
            if (player != null) {
                PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 0.5f, 1.0f);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult itemFrameRemoveItem(ItemFrameEntity itemFrame, @Nullable PlayerEntity player) {
        if (itemFrame.isInvisible() && itemFrame.getHeldItemStack().isEmpty()) {
            itemFrame.remove(RemovalReason.DISCARDED);
            Block.dropStack(itemFrame.getEntityWorld(), itemFrame.getBlockPos(), ItemFrameUtil.getAsItemStack(itemFrame));
            if (player != null) {
                PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 0.5f, 1.0f);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
