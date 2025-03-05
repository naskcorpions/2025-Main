// INFO: ROBOT IMPORTS
package frc.robot.Constants;

public final class ControllerConstants {
    public final class driveController {
        public static final int kDriverControllerPort = 0;
        public static final double kDriveDeadband = 0.05;

        public static final int kDriverDefenseButton = 1;
        public static final int kDriverAutoAllignButton = 2;
        public static final int kDriverRobotOrientedDriveButton = 6;
        
        public static final int kDriverPathRunButton = 4;

    }

    public final class operatorController {
        public static final int kOperatorControllerPort = 1;
        public static final double kOperatorDeadband = 0.05;

        public static final int kOperatorPivotIntakePoseButton = 1;
        public static final int kOperatorPivotOuttakePoseButton = 2;
        public static final int kOperatorPivotStopButton = 3;
    }
}
