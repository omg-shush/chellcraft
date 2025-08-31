package com.chellrose.chellcraft.features.armorstand;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.chellrose.chellcraft.ChellCraft;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
        Item modifier = player.getStackInHand(hand).getItem();
        if (modifier.equals(Items.STICK)) {
            // Add arms to the armor stand
        } else if (modifier.equals(Items.SHEARS)) {
            // Remove arms from the armor stand
        } else if (PICKAXES.contains(modifier)) {
            // Remove the base plate from the armor stand
        } else if (modifier.equals(Items.SMOOTH_STONE_SLAB)) {
            // Add the base plate to the armor stand
        } else if (ArmorStandPose.MUSIC_DISC_POSES.containsKey(modifier)) {
            // Apply the pose to the armor stand
            ArmorStandPose pose = ArmorStandPose.MUSIC_DISC_POSES.get(modifier);
        }
        return ActionResult.SUCCESS;
    }
}
