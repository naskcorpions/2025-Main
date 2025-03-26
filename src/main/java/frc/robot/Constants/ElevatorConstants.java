// INFO: ROBOT IMPORTS
package frc.robot.Constants;

public final class ElevatorConstants {
    public final class Elevator {
        // Motor Number
        public static final int kElevatorLeftMotor = 10;
        public static final int kElevatorRightMotor = 9;
        // Max Speeds
        public static final double maxElevatorSpeed = 0.2;
        // PID Values
        public static final double kP = 1.1;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        // Stall Limits
        public static final int kStallLimit = 40;
        // Positions
        // REVIEW: NOTE: Set values to elevator
        public static final double kElevatorBottomPose = 0.0;
        public static final double kElevatorL1CoralPose = 0.0;
        public static final double kElevatorL2CoralPose = 0.0;
        public static final double kElevatorL3CoralPose = 152;
        public static final double kElevatorL4CoralPose = 0.0;
        public static final double kElevatorTopPose = 0.0;
    }

    public final class Intake {
        // Motor Number
        public static final int kIntakeMotor = 12;
        public static final int kStallLimit = 35;
        
        // NEW: Intake motor speed constant
        public static final double intakeSpeed = 0.25; 
        
        // NEW: Reverse intake motor speed constant
        public static final double reverseIntakeSpeed = 0.4; 
        
    }
    public final class Pivot {
        // Motor Number

        public static final int kPivotMotorID = 11;
        // Stall Limits
        public static final int kStallLimit = 30;
        // IMPORTANT:
        // NOTE: SET VALUES
        // Closed Loop Values
        public static final double kP = 4;        
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final int kClosedLoopSlot = 0;
        // MaxMotion Limits
        public static final double kMaxAcceleration = 520; // MEASURE: RPM/s
        public static final double kMaxVelocity = 740; // MEASURE: RPM
        public static final double kAllowedClosedLoopError = 0.01; // MEASURE: REVIEW: Rotations? 
        // Positions
        public static final double kIntakePos = 0.328;
        public static final double kOuttakePose = 0.17;
        public static final double kL4Pose = 0.34;

    }

}
