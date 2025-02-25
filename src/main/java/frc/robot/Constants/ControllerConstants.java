// INFO: ROBOT IMPORTS
package frc.robot.Constants;

public final class ControllerConstants {
    public final class driveController {
        public static final int kDriverControllerPort = 0;
        public static final double kDriveDeadband = 0.05;

        public static final int kDriverDefenseButton = 1; //Set to Right Bumper
        public static final int kDriverAutoAllignButton = 2; // Set to Left Bumper
        
        public static final int kDriverPathRunButton = 4;

    }

    public final class operatorController {
        public static final int kOperatorControllerPort = 1;
        public static final double kOperatorDeadband = 0.05;
    }
}
