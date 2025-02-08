package frc.robot.Constants;

public class ControllerConfig{
    public class driveController {
        public static int kDriverControllerPort = 0;
        public static double kDriveDeadband = 0.05;
        
        public static int kDriverDefenseButton = 1; //Set to Right Bumper
        public static int kDriverAutoAllignButton = 2; // Set to Left Bumper
        
        public static int kDriverPathRunButton = 4;
        
        public static int kDriverFollowSimpleAuto = 3;
        
        public static int kDriverDriveRobotRelative = 6;
        public static int kDriveToTag = 5;
        
    }
    
    public class operatorController {
        public static int kOperatorControllerPort = 1;
        public static double kOperatorDeadband = 0.05;
    }


}
