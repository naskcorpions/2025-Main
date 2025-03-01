// INFO: ROBOT IMPORTS
package frc.robot.Constants;
import javax.print.attribute.standard.MediaSize.Other;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation2d;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.AprilTagData;


public final class PathPlannerConstants {
    private static AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
    public final class PathConstraints {
        // M/S
        public final static double maxTranslationSpeed = 0.5;
        public final static double maxTranslationAcc= 0.2;
        // RADs/S
        public final static double maxRotationSpeed = 1 * Math.PI;
        public final static double maxRotationAcc= 0.5 * Math.PI;
    }
    public final class Offsets {
        // Calculated blue side
        public static final Transform2d leftWallOffset = new Transform2d();
        public static final Transform2d rightWallOffset = new Transform2d(0, OtherConstants.halfRobotPerimeterMeters, new Rotation2d());
        public static final Transform2d leftLoadStationOffset = new Transform2d();
        public static final Transform2d rightLoadStationOffset = new Transform2d();
        public static final Transform2d bargeOffset = new Transform2d();
        public static final Transform2d coralFront = new Transform2d(-OtherConstants.halfRobotPerimeterMeters, 0, new Rotation2d());
        public static final Transform2d coralLeft = new Transform2d();
        public static final Transform2d coralRight = new Transform2d();
        public static final Transform2d coralBack = new Transform2d(OtherConstants.halfRobotPerimeterMeters, 0, new Rotation2d());

        // Offsets for each coral placing thing from the tag
        public static final Transform2d coralPlaceLeft = new Transform2d();
        public static final Transform2d coralPlaceRight = new Transform2d();

    }
    public final class AprilTag {
        public static final AprilTagData aprilTag1  = new AprilTagData(1,  null, null, null);
        public static final AprilTagData aprilTag2  = new AprilTagData(2,  null, null, null);
        public static final AprilTagData aprilTag3  = new AprilTagData(3,  null, null, null);
        public static final AprilTagData aprilTag4  = new AprilTagData(4,  null, null, null);
        public static final AprilTagData aprilTag5  = new AprilTagData(5,  null, null, null);
        public static final AprilTagData aprilTag6  = new AprilTagData(6,  null, null, null);
        public static final AprilTagData aprilTag7  = new AprilTagData(7,  null, null, null);
        public static final AprilTagData aprilTag8  = new AprilTagData(8,  null, null, null);
        public static final AprilTagData aprilTag9  = new AprilTagData(9,  null, null, null);
        public static final AprilTagData aprilTag10 = new AprilTagData(10, null, null, null);
        public static final AprilTagData aprilTag11 = new AprilTagData(11, null, null, null);
        public static final AprilTagData aprilTag12 = new AprilTagData(12, null, null, null);
        public static final AprilTagData aprilTag13 = new AprilTagData(13, null, null, null);
        public static final AprilTagData aprilTag14 = new AprilTagData(14, null, null, null);
        public static final AprilTagData aprilTag15 = new AprilTagData(15, null, null, null);
        public static final AprilTagData aprilTag16 = new AprilTagData(16, null, null, null);
        public static final AprilTagData aprilTag17 = new AprilTagData(17, null, null, null);
        public static final AprilTagData aprilTag18 = new AprilTagData(18, null, null, null);
        public static final AprilTagData aprilTag19 = new AprilTagData(19, null, null, null);
        public static final AprilTagData aprilTag20 = new AprilTagData(20, null, null, null);
        public static final AprilTagData aprilTag21 = new AprilTagData(21, null, new Transform2d(0.127*4, 0, new Rotation2d()), null);
        public static final AprilTagData aprilTag22 = new AprilTagData(22, null, null, null);
    }
}
