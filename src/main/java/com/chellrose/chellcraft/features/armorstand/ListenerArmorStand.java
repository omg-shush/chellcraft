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
            return ActionResult.SUCCESS;
        }

        // Modify armor stand while sneaking
        ArmorStandEntity armorStand = (ArmorStandEntity) entity;
        Item modifier = player.getStackInHand(hand).getItem();
        if (modifier.equals(Items.STICK)) {
            return this.addArms(player, hand, armorStand);
        } else if (modifier.equals(Items.SHEARS)) {
            return this.removeArms(player, hand, armorStand);
        } else if (modifier.equals(Items.SMOOTH_STONE_SLAB)) {
            return this.addBasePlate(player, hand, armorStand);
        } else if (PICKAXES.contains(modifier)) {
            return this.removeBasePlate(player, hand, armorStand);
        } else if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(modifier)) {
            return this.applyPose(player, hand, armorStand);
        }
        return ActionResult.PASS;
    }

    private ActionResult addArms(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Logic to add arms to the armor stand
        ItemStack handStack = player.getStackInHand(hand);
        if (!armorStand.shouldShowArms() && handStack.getCount() >= 2) {
            handStack.decrement(2);
            if (handStack.getCount() <= 0) {
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            armorStand.setShowArms(true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult removeArms(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Logic to remove arms from the armor stand
        if (armorStand.shouldShowArms()) {
            player.getStackInHand(hand).damage(2, player, hand);
            armorStand.setShowArms(false);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private ActionResult addBasePlate(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Logic to add a base plate to the armor stand
        return ActionResult.SUCCESS;
    }

    private ActionResult removeBasePlate(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        // Logic to remove a base plate from the armor stand
        return ActionResult.SUCCESS;
    }

    private ActionResult applyPose(PlayerEntity player, Hand hand, ArmorStandEntity armorStand) {
        ItemStack handStack = player.getStackInHand(hand);
        Item item = handStack.getItem();
        if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(item)) {
            ArmorStandPose pose = ArmorStandPose.MUSIC_DISC_POSES.get(item);
            pose.apply(armorStand);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
