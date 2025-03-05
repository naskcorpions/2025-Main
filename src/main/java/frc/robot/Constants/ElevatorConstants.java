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
        public static final int kIntakeMotor = 11;
        public static final int kStallLimit = 15;
        
        // NEW: Intake motor speed constant
        public static final double intakeSpeed = 0.4; 
        
        // NEW: Constants for the joint motor.
        public static final int jointMotor = 13; // Set this to the correct port.
        public static final double jointUpperLimit = 100.0; // Adjust upper limit value.
        public static final double jointLowerLimit = 0.0;   // Adjust lower limit value.
        public static final double jointSpeed = 0.1;        // Adjust speed value.
    }
    public final class Pivot {
        // Motor Number
        public static final int kPivotMotor = 13;
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
