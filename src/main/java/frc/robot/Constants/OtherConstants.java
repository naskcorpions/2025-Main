// INFO: ROBOT IMPORTS
package frc.robot.Constants;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;


/** Class for other/misc. constants */
public final class OtherConstants {
    public static final Pose2d kRobotStartingPose = new Pose2d(12.217, 4.013, new Rotation2d(Math.toRadians(180)));
    public static final double robotPerimeterInches = 30;
    public static final double robotPerimeterMeters = Units.inchesToMeters(robotPerimeterInches);
    public static final double halfRobotPerimeterInches = robotPerimeterInches / 2;
    public static final double halfRobotPerimeterMeters = robotPerimeterMeters / 2;

    public final class DashboardConstants{
        public static final String autoTabName = "Autonomus";
        public static final String teleopTabName = "Teleoperated";
        public static final String initTabName = "Initialize";
        public static final boolean switchTabs = true;
    }
    
    /** Constants for the PathPlanner AutoBuilder */
    public final class AutoBuilderConstants {
        // IMPORTANT:
        // NOTE: TUNE PID CONSTANTS
        // INFO: TRANSLATION PID
        public static final double kTranslationP = 5;
        public static final double kTranslationI = 0.0;
        public static final double kTranslationD = 0.0;
        // INFO: ROTATION PID
        public static final double kRotationP = .2;
        public static final double kRotationI = 0.0;
        public static final double kRotationD = 0.0;
    }

}
