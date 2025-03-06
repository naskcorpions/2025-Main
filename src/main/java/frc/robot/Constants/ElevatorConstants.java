// INFO: ROBOT IMPORTS
package frc.robot.Constants;

public final class ElevatorConstants {
    public final class Elevator {
        // Motor Number
        public static final int kElevatorLeftMotor = 9;
        public static final int kElevatorRightMotor = 10;
        // Stall Limits
        public static final int kStallLimit = 40;
        // Positions
        // REVIEW: NOTE: Set values to elevator
        public static final double kElevatorBottonPose = 0.0;
        public static final double kElevatorL1CoralPose = 0.0;
        public static final double kElevatorL2CoralPose = 0.0;
        public static final double kElevatorL3CoralPose = 0.0;
        public static final double kElevatorL4CoralPose = 0.0;
        public static final double kElevatorTopPose = 0.0;
    }

    public final class Intake {
        // Motor Number
        public static final int kIntakeMotor = 12;
        public static final int kStallLimit = 15;
        
        // NEW: Intake motor speed constant
        public static final double intakeSpeed = 0.2; 
        
        // NEW: Reverse intake motor speed constant
        public static final double reverseIntakeSpeed = 0.6; 
        
    }
    public final class Pivot {
        // Motor Number
        // public static final int kPivotMotor = 12;
        // Stall Limits
        public static final int kStallLimit = 25;
        // IMPORTANT:
        // NOTE: SET VALUES
        // Closed Loop Values
        public static final double kP = 0.0;        
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final int kClosedLoopSlot = 0;
        // MaxMotion Limits
        public static final double kMaxAcceleration = 0.0; // MEASURE: RPM/s
        public static final double kMaxVelocity = 0.0; // MEASURE: RPM
        public static final double kAllowedClosedLoopError = 0.0; // MEASURE: REVIEW: Rotations? 
        // Positions


    }

}
