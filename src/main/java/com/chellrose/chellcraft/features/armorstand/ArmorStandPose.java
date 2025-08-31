package com.chellrose.chellcraft.features.armorstand;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.EulerAngle;

/**
 * Stores the euler angles used for an Armor Stand pose.
 */
public class ArmorStandPose {
    private static final EulerAngle ZERO_ANGLE = new EulerAngle(0f, 0f, 0f);

    public static final ArmorStandPose STRAIGHT_POSE = new ArmorStandPose(
        new EulerAngle(0.017453292519943295f, 0.0f, 0.0f),
        new EulerAngle(0.017453292519943295f, 0.0f, 0.0f),
        new EulerAngle(0.017453292519943295f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKHANDS_POSE = new ArmorStandPose(
        new EulerAngle(0.33161255787892263f, 0.0f, 0.0f),
        new EulerAngle(5.201081170943102f, 0.5061454830783556f, 0.0f),
        new EulerAngle(5.375614096142535f, 5.724679946541401f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose LOOKATTHIS_POSE = new ArmorStandPose(
        new EulerAngle(6.073745796940266f, 0.0f, 0.0f),
        new EulerAngle(0.2792526803190927f, 0.20943951023931956f, 0.0f),
        new EulerAngle(5.672320068981571f, 0.5061454830783556f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose WAIT_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(0.5061454830783556f, 0.0f, 0.0f),
        new EulerAngle(4.031710572106901f, 0.0f, 0.0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose PRAY_POSE = new ArmorStandPose(
        new EulerAngle(5.724679946541401f, 0.0f, 0.0f),
        new EulerAngle(3.857177646907468f, 0.0f, 5.619960191421741f),
        new EulerAngle(3.857177646907468f, 0.0f, 0.6806784082777885f),
        new EulerAngle(0.03490658503988659f, 0.0f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose HUG_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(5.323254218582705f, 0.0f, 4.084070449666731f),
        new EulerAngle(4.084070449666731f, 0.0f, 5.323254218582705f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );

    public static final ArmorStandPose DAB_POSE = new ArmorStandPose(
        new EulerAngle(0.5585053606381855f, 0.0f, 0.0f),
        new EulerAngle(4.328416544945937f, 5.445427266222308f, 0.0f),
        new EulerAngle(4.4331363000655974f, 5.672320068981571f, 0.0f),
        new EulerAngle(0.0f, 0.5585053606381855f, 0.0f),
        ZERO_ANGLE
    );

    public static final ArmorStandPose BRUH_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(4.4331363000655974f, 0.0f, 0.0f),
        new EulerAngle(4.328416544945937f, 0.0f, 0.0f),
        ZERO_ANGLE,
        new EulerAngle(5.550147021341968f, 0.0f, 0.0f)
    );

    public static final ArmorStandPose DANCE1_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(0.0f, 0.0f, 4.153883619746504f),
        new EulerAngle(0.0f, 0.0f, 2.0943951023931953f),
        new EulerAngle(6.19591884457987f, 0.7330382858376184f, 0.0f),
        new EulerAngle(4.4331363000655974f, 1.2740903539558606f, 0.0f)
    );

    public static final ArmorStandPose DANCE2_POSE = new ArmorStandPose(
        ZERO_ANGLE,
        new EulerAngle(0.0f, 0.0f, 4.153883619746504f),
        new EulerAngle(0.0f, 0.0f, 2.0943951023931953f),
        new EulerAngle(4.6774823953448035f, 5.550147021341968f, 0.0f),
        new EulerAngle(0.03490658503988659f, 1.5707963267948966f, 0.0f)
    );

    public static final ArmorStandPose PROPOSE_POSE = new ArmorStandPose(
        new EulerAngle(0.0f, 1.1519173063162575f, 0.0f),
        new EulerAngle(6.09119908946021f, 0.0f, 0.0f),
        new EulerAngle(3.263765701229396f, 0.0f, 5.026548245743669f),
        new EulerAngle(0.03490658503988659f, 0.7330382858376184f, 0.0f),
        new EulerAngle(0.0f, 0.5585053606381855f, 0.0f)
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

    private final EulerAngle headAngles;
    private final EulerAngle leftArmAngles;
    private final EulerAngle rightArmAngles;
    private final EulerAngle leftLegAngles;
    private final EulerAngle rightLegAngles;

    // Accepts Euler angles for all posable parts of an Armor Stand in degrees
    public ArmorStandPose(EulerAngle headAngles, EulerAngle leftArmAngles, EulerAngle rightArmAngles, EulerAngle leftLegAngles, EulerAngle rightLegAngles) {
        this.headAngles     = new EulerAngle((float) Math.toRadians(headAngles.pitch()),     (float) Math.toRadians(headAngles.yaw()),     (float) Math.toRadians(headAngles.roll()));
        this.leftArmAngles  = new EulerAngle((float) Math.toRadians(leftArmAngles.pitch()),  (float) Math.toRadians(leftArmAngles.yaw()),  (float) Math.toRadians(leftArmAngles.roll()));
        this.rightArmAngles = new EulerAngle((float) Math.toRadians(rightArmAngles.pitch()), (float) Math.toRadians(rightArmAngles.yaw()), (float) Math.toRadians(rightArmAngles.roll()));
        this.leftLegAngles  = new EulerAngle((float) Math.toRadians(leftLegAngles.pitch()),  (float) Math.toRadians(leftLegAngles.yaw()),  (float) Math.toRadians(leftLegAngles.roll()));
        this.rightLegAngles = new EulerAngle((float) Math.toRadians(rightLegAngles.pitch()), (float) Math.toRadians(rightLegAngles.yaw()), (float) Math.toRadians(rightLegAngles.roll()));
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
