package com.chellrose.chellcraft.features.armorstand;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Stores the euler angles used for an Armor Stand pose.
 */
public class ArmorStandPose {    
    public static final Rotations ZERO_ANGLE = new Rotations(0f, 0f, 0f);

    public static final ArmorStandPose STRAIGHT_POSE = new ArmorStandPose(
        new Rotations(1.0f, 0.0f, 0.0f),
        new Rotations(1.0f, 0.0f, 0.0f),
        new Rotations(1.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKHANDS_POSE = new ArmorStandPose(
        new Rotations(19.0f, 0.0f, 0.0f),
        new Rotations(298.0f, 29.0f, 0.0f),
        new Rotations(308.0f, 328.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKATTHIS_POSE = new ArmorStandPose(
        new Rotations(348.0f, 0.0f, 0.0f),
        new Rotations(16.0f, 12.0f, 0.0f),
        new Rotations(325.0f, 29.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose WAIT_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new Rotations(29.0f, 0.0f, 0.0f),
        new Rotations(231.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose PRAY_POSE = new ArmorStandPose(
        new Rotations(328.0f, 0.0f, 0.0f),
        new Rotations(221.0f, 0.0f, 322.0f),
        new Rotations(221.0f, 0.0f, 39.0f),
        new Rotations(2.0f, 0.0f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose HUG_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new Rotations(305.0f, 0.0f, 234.0f),
        new Rotations(234.0f, 0.0f, 305.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose DAB_POSE = new ArmorStandPose(
        new Rotations(32.0f, 0.0f, 0.0f),
        new Rotations(248.0f, 312.0f, 0.0f),
        new Rotations(254.0f, 325.0f, 0.0f),
        new Rotations(0.0f, 32.0f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose BRUH_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new Rotations(254.0f, 0.0f, 0.0f),
        new Rotations(248.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        new Rotations(318.0f, 0.0f, 0.0f)
    );

    public static final ArmorStandPose DANCE1_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new Rotations(0.0f, 0.0f, 238.0f),
        new Rotations(0.0f, 0.0f, 120.0f),
        new Rotations(355.0f, 42.0f, 0.0f),
        new Rotations(254.0f, 73.0f, 0.0f)
    );

    public static final ArmorStandPose DANCE2_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new Rotations(0.0f, 0.0f, 238.0f),
        new Rotations(0.0f, 0.0f, 120.0f),
        new Rotations(268.0f, 318.0f, 0.0f),
        new Rotations(2.0f, 90.0f, 0.0f)
    );

    public static final ArmorStandPose PROPOSE_POSE = new ArmorStandPose(
        new Rotations(0.0f, 66.0f, 0.0f),
        new Rotations(349.0f, 0.0f, 0.0f),
        new Rotations(187.0f, 0.0f, 288.0f),
        new Rotations(2.0f, 42.0f, 0.0f),
        new Rotations(0.0f, 32.0f, 0.0f)
    );

    private static final ArmorStandPose[] POSES = {
        STRAIGHT_POSE,
        LOOKHANDS_POSE,
        LOOKATTHIS_POSE,
        WAIT_POSE,
        PRAY_POSE,
        HUG_POSE,
        DAB_POSE,
        BRUH_POSE,
        DANCE1_POSE,
        DANCE2_POSE,
        PROPOSE_POSE
    };

    public static final Map<Item, ArmorStandPose> MUSIC_DISC_POSES = new HashMap<>();
    static {
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_CAT,     ArmorStandPose.LOOKHANDS_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_BLOCKS,  ArmorStandPose.LOOKATTHIS_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_CHIRP,   ArmorStandPose.PRAY_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_FAR,     ArmorStandPose.HUG_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_MALL,    ArmorStandPose.DAB_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_MELLOHI, ArmorStandPose.BRUH_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_STAL,    ArmorStandPose.DANCE1_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_STRAD,   ArmorStandPose.DANCE2_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_WARD,    ArmorStandPose.PROPOSE_POSE);
        MUSIC_DISC_POSES.put(Items.MUSIC_DISC_WAIT,    ArmorStandPose.WAIT_POSE);
    }

    private final Rotations headAngles;
    private final Rotations leftArmAngles;
    private final Rotations rightArmAngles;
    private final Rotations leftLegAngles;
    private final Rotations rightLegAngles;

    // Accepts Euler angles for all posable parts of an Armor Stand
    public ArmorStandPose(Rotations headAngles, Rotations leftArmAngles, Rotations rightArmAngles, Rotations leftLegAngles, Rotations rightLegAngles) {
        this.headAngles     = headAngles;
        this.leftArmAngles  = leftArmAngles;
        this.rightArmAngles = rightArmAngles;
        this.leftLegAngles  = leftLegAngles;
        this.rightLegAngles = rightLegAngles;
    }

    // Construct ArmorStandPose based on an existing ArmorStandEntity
    public ArmorStandPose(ArmorStand armorStand) {
        this.headAngles     = armorStand.getHeadPose();
        this.leftArmAngles  = armorStand.getLeftArmPose();
        this.rightArmAngles = armorStand.getRightArmPose();
        this.leftLegAngles  = armorStand.getLeftLegPose();
        this.rightLegAngles = armorStand.getRightLegPose();
    }

    /**
     * Applies this pose to the given ArmorStand.
     *
     * @param armorStand the ArmorStandEntity to apply this pose to
     */
    public void apply(ArmorStand armorStand) {
        if (this.headAngles != null) {
            armorStand.setHeadPose(this.headAngles);
        }
        if (this.leftArmAngles != null) {
            armorStand.setLeftArmPose(this.leftArmAngles);
        }
        if (this.rightArmAngles != null) {
            armorStand.setRightArmPose(this.rightArmAngles);
        }
        if (this.leftLegAngles != null) {
            armorStand.setLeftLegPose(this.leftLegAngles);
        }
        if (this.rightLegAngles != null) {
            armorStand.setRightLegPose(this.rightLegAngles);
        }
    }

    public boolean equals(ArmorStandPose other) {
        return other.headAngles != null && this.headAngles.equals(other.headAngles) &&
               other.leftArmAngles != null && this.leftArmAngles.equals(other.leftArmAngles) &&
               other.rightArmAngles != null && this.rightArmAngles.equals(other.rightArmAngles) &&
               other.leftLegAngles != null && this.leftLegAngles.equals(other.leftLegAngles) &&
               other.rightLegAngles != null && this.rightLegAngles.equals(other.rightLegAngles);
    }

    /**
     * Returns the index of the current pose in the list of available poses.
     *
     * @return The index of the current pose, or -1 if the pose is not found.
     */
    public int index() {
        for (int i = 0; i < POSES.length; i++) {
            if (this.equals(POSES[i])) {
                return i;
            }
        }
        return -1;
    }

    public static ArmorStandPose fromIndex(int index) {
        return POSES[Math.floorMod(index, POSES.length)];
    }
}
