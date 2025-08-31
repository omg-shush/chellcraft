import re
import math

file_path = "./poses.txt"

with open(file_path, "r") as file:
    lines = file.readlines()

for line in lines:
    match = re.search(r'@e\[name=(.*?)\]', line)
    if match:
        name = match.group(1)
        poses = {}
        pose_matches = re.findall(r'(\w+):\[(\d+(?:\.\d+)?)f?,(\d+(?:\.\d+)?)f?,(\d+(?:\.\d+)?)f?\]', line)
        for pose_match in pose_matches:
            pose_type = pose_match[0]
            pose_values = [float(pose_match[1]), float(pose_match[2]), float(pose_match[3])]
            poses[pose_type] = pose_values

    """
    public static final ArmorStandPose STRAIGHT_POSE = new ArmorStandPose(
        new EulerAngle(1f, 0f, 0f),
        new EulerAngle(1f, 0f, 0f),
        new EulerAngle(1f, 0f, 0f),
        ZERO_ANGLE,
        ZERO_ANGLE
    );
    """
    head_pose = [math.radians(angle) for angle in poses.get("Head", [0.0, 0.0, 0.0])]
    left_arm_pose = [math.radians(angle) for angle in poses.get("LeftArm", [0.0, 0.0, 0.0])]
    right_arm_pose = [math.radians(angle) for angle in poses.get("RightArm", [0.0, 0.0, 0.0])]
    left_leg_pose = [math.radians(angle) for angle in poses.get("LeftLeg", [0.0, 0.0, 0.0])]
    right_leg_pose = [math.radians(angle) for angle in poses.get("RightLeg", [0.0, 0.0, 0.0])]

    print(f"public static final ArmorStandPose {name.upper()}_POSE = new ArmorStandPose(")
    print(f"    {'new EulerAngle(' + ', '.join(map(lambda p: str(p) + 'f', head_pose)) + ')' if head_pose != [0.0, 0.0, 0.0] else 'ZERO_ANGLE'},")
    print(f"    {'new EulerAngle(' + ', '.join(map(lambda p: str(p) + 'f', left_arm_pose)) + ')' if left_arm_pose != [0.0, 0.0, 0.0] else 'ZERO_ANGLE'},")
    print(f"    {'new EulerAngle(' + ', '.join(map(lambda p: str(p) + 'f', right_arm_pose)) + ')' if right_arm_pose != [0.0, 0.0, 0.0] else 'ZERO_ANGLE'},")
    print(f"    {'new EulerAngle(' + ', '.join(map(lambda p: str(p) + 'f', left_leg_pose)) + ')' if left_leg_pose != [0.0, 0.0, 0.0] else 'ZERO_ANGLE'},")
    print(f"    {'new EulerAngle(' + ', '.join(map(lambda p: str(p) + 'f', right_leg_pose)) + ')' if right_leg_pose != [0.0, 0.0, 0.0] else 'ZERO_ANGLE'}")
    print(");")
    print()
