package frc.robot.Constants;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Rotation3d;

public class VisionConstants {

    public class RPI1 {
        public static final double kCameraHeight = 0.55;
        public static final double kTargetHeight = 0.22;
        public static final double kCameraPitch = 0;
    
        public static final String kCameraName = "picam";
        public static final String kRPIIP = "10.25.94.11";
        public static final String kHostname = "photonvision";

        // Camera pos in meters relative to the center of the robot
        public static final Transform3d kCameraToRobot = new Transform3d(0.381, 0.0, 0.0, new Rotation3d());
    }
    
    public class RPI2 {
        public static final double kCameraHeight = 0.55;
        public static final double kTargetHeight = 0.22;
        public static final double kCameraPitch = 0;

        public static final String kCameraName = "picam2";
        public static final String kRPIIP = "10.25.94.12";
        public static final String kHostname = "photonvision2";
        
        // TODO: SET
        // Camera pos in meters relative to the center of the robot
        public static final Transform3d kCameraToRobot = new Transform3d(0, 0.0, 0.0, new Rotation3d());
    }
    
}
