// INFO: ROBOT IMPORTS
package frc.robot.Constants;
// INFO: WPILIB IMPORTS
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;


/** Class for other/misc. constants */
public final class OtherConstants {
    public static final Pose2d kRobotStartingPose = new Pose2d(2.359, 0.817, new Rotation2d(0));

    public final class DashboardConstants{
        public static final String autoTabName = "Auto";
        public static final String teleopTabName = "Teleoperated";
    }
    
    /** Constants for the PathPlanner AutoBuilder */
    public final class AutoBuilderConstants {
        // IMPORTANT:
        // NOTE: TUNE PID CONSTANTS
        // INFO: TRANSLATION PID
        public static final double kTranslationP = 0.5;
        public static final double kTranslationI = 0.0;
        public static final double kTranslationD = 0.0;
        // INFO: ROTATION PID
        public static final double kRotationP = 0.001;
        public static final double kRotationI = 0.0;
        public static final double kRotationD = 0.0005;
    }

}
