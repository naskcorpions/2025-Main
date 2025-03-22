// INFO: ROBOT IMPORTS
package frc.robot.Constants;
import javax.print.attribute.standard.MediaSize.Other;

import edu.wpi.first.math.geometry.Rotation2d;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;


public final class PathPlannerConstants {
    public final class PathConstraints {
        // M/S
        public final static double maxTranslationSpeed = 1.3;
        public final static double maxTranslationAcc= 0.3;
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
        public static final Transform2d coralBack = new Transform2d(OtherConstants.halfRobotPerimeterInches, 0, new Rotation2d());

        // Offsets for each coral placing thing from the tag
        public static final Transform2d coralPlaceLeft = new Transform2d();
        public static final Transform2d coralPlaceRight = new Transform2d();

    }
}
