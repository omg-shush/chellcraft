package com.chellrose.chellcraft.features.armorstand;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.EulerAngle;

/**
 * Stores the euler angles used for an Armor Stand pose.
 */
public class ArmorStandPose {    
    public static final EulerAngle ZERO_ANGLE = new EulerAngle(0f, 0f, 0f);

    public static final ArmorStandPose STRAIGHT_POSE = new ArmorStandPose(
        new EulerAngle(1.0f, 0.0f, 0.0f),
        new EulerAngle(1.0f, 0.0f, 0.0f),
        new EulerAngle(1.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKHANDS_POSE = new ArmorStandPose(
        new EulerAngle(19.0f, 0.0f, 0.0f),
        new EulerAngle(298.0f, 29.0f, 0.0f),
        new EulerAngle(308.0f, 328.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKATTHIS_POSE = new ArmorStandPose(
        new EulerAngle(348.0f, 0.0f, 0.0f),
        new EulerAngle(16.0f, 12.0f, 0.0f),
        new EulerAngle(325.0f, 29.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose WAIT_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(29.0f, 0.0f, 0.0f),
        new EulerAngle(231.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose PRAY_POSE = new ArmorStandPose(
        new EulerAngle(328.0f, 0.0f, 0.0f),
        new EulerAngle(221.0f, 0.0f, 322.0f),
        new EulerAngle(221.0f, 0.0f, 39.0f),
        new EulerAngle(2.0f, 0.0f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose HUG_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(305.0f, 0.0f, 234.0f),
        new EulerAngle(234.0f, 0.0f, 305.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose DAB_POSE = new ArmorStandPose(
        new EulerAngle(32.0f, 0.0f, 0.0f),
        new EulerAngle(248.0f, 312.0f, 0.0f),
        new EulerAngle(254.0f, 325.0f, 0.0f),
        new EulerAngle(0.0f, 32.0f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose BRUH_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(254.0f, 0.0f, 0.0f),
        new EulerAngle(248.0f, 0.0f, 0.0f),
        ZERO_ANGLE,
        new EulerAngle(318.0f, 0.0f, 0.0f)
    );

    public static final ArmorStandPose DANCE1_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(0.0f, 0.0f, 238.0f),
        new EulerAngle(0.0f, 0.0f, 120.0f),
        new EulerAngle(355.0f, 42.0f, 0.0f),
        new EulerAngle(254.0f, 73.0f, 0.0f)
    );

    public static final ArmorStandPose DANCE2_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(0.0f, 0.0f, 238.0f),
        new EulerAngle(0.0f, 0.0f, 120.0f),
        new EulerAngle(268.0f, 318.0f, 0.0f),
        new EulerAngle(2.0f, 90.0f, 0.0f)
    );

    public static final ArmorStandPose PROPOSE_POSE = new ArmorStandPose(
        new EulerAngle(0.0f, 66.0f, 0.0f),
        new EulerAngle(349.0f, 0.0f, 0.0f),
        new EulerAngle(187.0f, 0.0f, 288.0f),
        new EulerAngle(2.0f, 42.0f, 0.0f),
        new EulerAngle(0.0f, 32.0f, 0.0f)
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

    private final EulerAngle headAngles;
    private final EulerAngle leftArmAngles;
    private final EulerAngle rightArmAngles;
    private final EulerAngle leftLegAngles;
    private final EulerAngle rightLegAngles;

    // Accepts Euler angles for all posable parts of an Armor Stand
    public ArmorStandPose(EulerAngle headAngles, EulerAngle leftArmAngles, EulerAngle rightArmAngles, EulerAngle leftLegAngles, EulerAngle rightLegAngles) {
        this.headAngles     = headAngles;
        this.leftArmAngles  = leftArmAngles;
        this.rightArmAngles = rightArmAngles;
        this.leftLegAngles  = leftLegAngles;
        this.rightLegAngles = rightLegAngles;
    }

    // Construct ArmorStandPose based on an existing ArmorStandEntity
    public ArmorStandPose(ArmorStandEntity armorStand) {
        this.headAngles     = armorStand.getHeadRotation();
        this.leftArmAngles  = armorStand.getLeftArmRotation();
        this.rightArmAngles = armorStand.getRightArmRotation();
        this.leftLegAngles  = armorStand.getLeftLegRotation();
        this.rightLegAngles = armorStand.getRightLegRotation();
    }

    /**
     * Applies this pose to the given ArmorStand.
     *
     * @param armorStand the ArmorStandEntity to apply this pose to
     */
    public void apply(ArmorStandEntity armorStand) {
        armorStand.setHeadRotation(this.headAngles);
        armorStand.setLeftArmRotation(this.leftArmAngles);
        armorStand.setRightArmRotation(this.rightArmAngles);
        armorStand.setLeftLegRotation(this.leftLegAngles);
        armorStand.setRightLegRotation(this.rightLegAngles);
    }

    public boolean equals(ArmorStandPose other) {
        return this.headAngles.equals(other.headAngles) &&
               this.leftArmAngles.equals(other.leftArmAngles) &&
               this.rightArmAngles.equals(other.rightArmAngles) &&
               this.leftLegAngles.equals(other.leftLegAngles) &&
               this.rightLegAngles.equals(other.rightLegAngles);
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
