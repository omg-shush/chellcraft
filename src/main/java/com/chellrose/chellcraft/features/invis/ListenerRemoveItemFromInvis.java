package com.chellrose.chellcraft.features.invis;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.callbacks.ArmorStandRemoveItemCallback;
import com.chellrose.chellcraft.callbacks.ItemFrameRemoveItemCallback;
import com.chellrose.chellcraft.util.ArmorStandUtil;
import com.chellrose.chellcraft.util.ItemFrameUtil;
import com.chellrose.chellcraft.util.PlayerSoundUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class ListenerRemoveItemFromInvis {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    public ListenerRemoveItemFromInvis() {
        ArmorStandRemoveItemCallback.EVENT.register(this::armorStandRemoveItem);
        ItemFrameRemoveItemCallback.EVENT.register(this::itemFrameRemoveItem);
    }

    private InteractionResult armorStandRemoveItem(ArmorStand armorStand, Player player) {
        if (armorStand.isInvisible() && !ArmorStandUtil.armorStandHasItems(armorStand)) {
            armorStand.remove(RemovalReason.DISCARDED);
            ItemStack itemStack = new ItemStack(Items.ARMOR_STAND);
            itemStack.set(DataComponents.CUSTOM_NAME, armorStand.getCustomName());
            Block.popResource(armorStand.level(), armorStand.blockPosition(), itemStack);
            if (player != null) {
                PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.VILLAGER_NO, SoundSource.BLOCKS, 0.5f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult itemFrameRemoveItem(ItemFrame itemFrame, @Nullable Player player) {
        if (itemFrame.isInvisible() && itemFrame.getItem().isEmpty()) {
            itemFrame.remove(RemovalReason.DISCARDED);
            Block.popResource(itemFrame.level(), itemFrame.blockPosition(), ItemFrameUtil.getAsItemStack(itemFrame));
            if (player != null) {
                PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.VILLAGER_NO, SoundSource.BLOCKS, 0.5f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
