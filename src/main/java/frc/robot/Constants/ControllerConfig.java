package frc.robot.Constants;

public class ControllerConfig{
    public class driveController {
        public static int kDriverControllerPort = 0;
        public static double kDriveDeadband = 0.05;
        
        public static int kDriverDefenseButton; //Set to Right Bumper
        public static int kDriverAutoAllignButton; // Set to Left Bumper
        
        public static int kDriverPathRunButton;
        
        public static int kDriverFollowSimpleAuto;
        
        public static int kDriverDriveRobotRelative;
        
        public static void checkID() {
            System.out.println(kDriverDefenseButton);
        }
    }
    
    public class operatorController {
        public static int kOperatorControllerPort = 1;
        public static double kOperatorDeadband = 0.05;
    }


}
