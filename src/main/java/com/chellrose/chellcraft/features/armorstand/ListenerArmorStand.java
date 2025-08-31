package com.chellrose.chellcraft.features.armorstand;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

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

    private ActionResult useEntity(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return ActionResult.PASS;
        }
        if (!player.isSneaking()) {
            return ActionResult.PASS;
        }

        // Modify armor stand while sneaking
        ArmorStandEntity armorStand = (ArmorStandEntity) entity;
        Item modifier = player.getStackInHand(hand).getItem();
        ActionResult result = ActionResult.PASS;
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

    private ActionResult addArms(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Add arms to the armor stand with 2 sticks
        ItemStack handStack = player.getStackInHand(hand);
        if (!armorStand.shouldShowArms() && handStack.getCount() >= 2) {
            handStack.decrement(2);
            if (handStack.getCount() <= 0) {
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            armorStand.setShowArms(true);
            player.playSoundToPlayer(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.BLOCKS, 0.5f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult removeArms(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Remove arms from the armor stand with shears, and drop any held items
        if (armorStand.shouldShowArms()) {
            player.getStackInHand(hand).damage(2, player, hand);
            armorStand.setShowArms(false);
            ItemStack armorStandMainHand = armorStand.getMainHandStack();
            ItemStack armorStandOffHand = armorStand.getOffHandStack();
            if (!armorStandMainHand.isEmpty()) {
                armorStand.dropItem(armorStandMainHand, false, false);
                armorStand.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }
            if (!armorStandOffHand.isEmpty()) {
                armorStand.dropItem(armorStandOffHand, false, false);
                armorStand.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
            player.playSoundToPlayer(SoundEvents.ITEM_SHEARS_SNIP, SoundCategory.BLOCKS, 0.5f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult addBasePlate(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Add a base plate to the armor stand with a smooth stone slab
        ItemStack handStack = player.getStackInHand(hand);
        if (!armorStand.shouldShowBasePlate()) {
            handStack.decrement(1);
            if (handStack.getCount() <= 0) {
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            armorStand.setHideBasePlate(false);
            player.playSoundToPlayer(SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 0.5f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult removeBasePlate(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Remove a base plate from the armor stand with a pickaxe
        if (armorStand.shouldShowBasePlate()) {
            player.getStackInHand(hand).damage(1, player, hand);
            armorStand.setHideBasePlate(true);
            player.playSoundToPlayer(SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 0.5f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult applyPose(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Apply a pose to the armor stand without consuming the music disc
        Item item = player.getStackInHand(hand).getItem();
        if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(item)) {
            ArmorStandPose.MUSIC_DISC_POSES.get(item).apply(armorStand);
            player.playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 0.5f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult cyclePose(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Cycle through the available poses for the armor stand
        ArmorStandPose currentPose = new ArmorStandPose(armorStand);
        ArmorStandPose nextPose = ArmorStandPose.fromIndex(currentPose.index() + 1);
        nextPose.apply(armorStand);
        player.playSoundToPlayer(SoundEvents.BLOCK_AMETHYST_CLUSTER_STEP, SoundCategory.BLOCKS, 0.5f, 1.0f);
        return ActionResult.SUCCESS;
    }

}
