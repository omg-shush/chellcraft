package com.chellrose.chellcraft.features.armorstand;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import com.chellrose.chellcraft.util.PlayerSoundUtil;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ListenerArmorStand {
    public static final Logger LOGGER = ChellCraft.LOGGER;

    private static final List<Item> PICKAXES = Arrays.asList(
        Items.WOODEN_PICKAXE,
        Items.STONE_PICKAXE,
        Items.IRON_PICKAXE,
        Items.GOLDEN_PICKAXE,
        Items.DIAMOND_PICKAXE,
        Items.NETHERITE_PICKAXE
    );

    public ListenerArmorStand() {
        UseEntityCallback.EVENT.register(this::useEntity);
    }

    private InteractionResult useEntity(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return InteractionResult.PASS;
        }
        if (!player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        // Modify armor stand while sneaking
        ArmorStand armorStand = (ArmorStand) entity;
        Item modifier = player.getItemInHand(hand).getItem();
        InteractionResult result = InteractionResult.PASS;
        if (modifier.equals(Items.STICK)) {
            result = this.addArms(player, hand, armorStand);
        } else if (modifier.equals(Items.SHEARS)) {
            result = this.removeArms(player, hand, armorStand);
        } else if (modifier.equals(Items.SMOOTH_STONE_SLAB)) {
            result = this.addBasePlate(player, hand, armorStand);
        } else if (PICKAXES.contains(modifier)) {
            result = this.removeBasePlate(player, hand, armorStand);
        } else if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(modifier)) {
            result = this.applyPose(player, hand, armorStand);
        } else if (modifier.equals(Items.DEBUG_STICK)) { // TODO add turbo encabulator
            result = this.cyclePose(player, hand, armorStand);
        }
        return result;
    }

    private InteractionResult addArms(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Add arms to the armor stand with 2 sticks
        ItemStack handStack = player.getItemInHand(hand);
        if (!armorStand.showArms() && handStack.getCount() >= 2) {
            handStack.shrink(2);
            if (handStack.getCount() <= 0) {
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
            armorStand.setShowArms(true);
            PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult removeArms(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Remove arms from the armor stand with shears, and drop any held items
        if (armorStand.showArms()) {
            player.getItemInHand(hand).hurtAndBreak(2, player, hand);
            armorStand.setShowArms(false);
            ItemStack armorStandMainHand = armorStand.getMainHandItem();
            ItemStack armorStandOffHand = armorStand.getOffhandItem();
            if (!armorStandMainHand.isEmpty()) {
                armorStand.drop(armorStandMainHand, false, false);
                armorStand.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
            if (!armorStandOffHand.isEmpty()) {
                armorStand.drop(armorStandOffHand, false, false);
                armorStand.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.SHEARS_SNIP, SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult addBasePlate(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Add a base plate to the armor stand with a smooth stone slab
        ItemStack handStack = player.getItemInHand(hand);
        if (!armorStand.showBasePlate()) {
            handStack.shrink(1);
            if (handStack.getCount() <= 0) {
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
            armorStand.setNoBasePlate(false);
            PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult removeBasePlate(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Remove a base plate from the armor stand with a pickaxe
        if (armorStand.showBasePlate()) {
            player.getItemInHand(hand).hurtAndBreak(1, player, hand);
            armorStand.setNoBasePlate(true);
            PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult applyPose(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Apply a pose to the armor stand without consuming the music disc
        Item item = player.getItemInHand(hand).getItem();
        if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(item)) {
            ArmorStandPose.MUSIC_DISC_POSES.get(item).apply(armorStand);
            PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 0.5f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private InteractionResult cyclePose(Player player, InteractionHand hand, ArmorStand armorStand) {
        // Cycle through the available poses for the armor stand
        ArmorStandPose currentPose = new ArmorStandPose(armorStand);
        ArmorStandPose nextPose = ArmorStandPose.fromIndex(currentPose.index() + 1);
        nextPose.apply(armorStand);
        PlayerSoundUtil.playSoundToPlayer(player, SoundEvents.AMETHYST_CLUSTER_STEP, SoundSource.BLOCKS, 0.5f, 1.0f);
        return InteractionResult.SUCCESS;
    }

}
