// INFO: ROBOT IMPORTS
package frc.robot;

//import frc.robot.Constants.ElevatorConstants;
// INFO: WPILIB IMPORTS
import frc.robot.Constants.SwerveConstants.ModuleConstants;

import com.revrobotics.spark.SparkBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public final class Configs {
    public static final class MAXSwerveModule {
        public static final SparkMaxConfig drivingConfig = new SparkMaxConfig();
        public static final SparkMaxConfig turningConfig = new SparkMaxConfig();
        
        static {
            // Use module constants to calculate conversion factors and feed forward gain.
            double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
            / ModuleConstants.kDrivingMotorReduction;
            double turningFactor = 2 * Math.PI;
            double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps / 2;
            
            drivingConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(40);
            drivingConfig.encoder
            .positionConversionFactor(drivingFactor) // meters
            .velocityConversionFactor(drivingFactor / 60.0); // meters per second
            drivingConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // These are example gains you may need to them for your own robot!
            .pid(0.04, 0, 0)
            .velocityFF(drivingVelocityFeedForward)
            .outputRange(-1, 1);
            
            turningConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20);
            turningConfig.absoluteEncoder
            // Invert the turning encoder, since the output shaft rotates in the opposite
            // direction of the steering motor in the MAXSwerve Module.
            .inverted(true)
            .positionConversionFactor(turningFactor) // radians
            .velocityConversionFactor(turningFactor / 60.0); // radians per second
            turningConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
            // These are example gains you may need to them for your own robot!
            .pid(1, 0, 0)
            .outputRange(-1, 1)
            // Enable PID wrap around for the turning motor. This will allow the PID
            // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
            // to 10 degrees will go through 0 rather than the other direction which is a
            // longer route.
            .positionWrappingEnabled(true)
            .positionWrappingInputRange(0, turningFactor);
        }
    }
    
    public static final class ElevatorConfig {
        public static final SparkMaxConfig elevatorLeftConfig = new SparkMaxConfig();
        public static final SparkMaxConfig elevatorRightConfig = new SparkMaxConfig();
                
        static {
            elevatorLeftConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(ElevatorConstants.Elevator.kStallLimit)
                // TODO SEE BELOW:
                // Will need to be set for each motor
                // One will need to be inverted, the other not
                .inverted(true)
                .follow(ElevatorConstants.Elevator.kElevatorRightMotor, true);
            
            elevatorLeftConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                // TODO: TUNE? FIGURE OUT WHAT TO DO WITH IT
                .pid(
                    ElevatorConstants.Elevator.kP,
                    ElevatorConstants.Elevator.kI,
                    ElevatorConstants.Elevator.kD)
                // TODO: FIGURE OUT FF. DO WE NEED IT? HOW DO WE USE IT?
                // .velocityFF(0)
                .outputRange(-ElevatorConstants.Elevator.maxElevatorSpeed, ElevatorConstants.Elevator.maxElevatorSpeed);
            
            elevatorRightConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(ElevatorConstants.Elevator.kStallLimit)
                // TODO SEE BELOW:
                // Will need to be set for each motor
                // One will need to be inverted, the other not
                .inverted(false);
            
            elevatorRightConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                // TODO: TUNE? FIGURE OUT WHAT TO DO WITH IT
                .p(ElevatorConstants.Elevator.kP)
                // TODO: FIGURE OUT FF. DO WE NEED IT? HOW DO WE USE IT?
                // .velocityFF(0)
                .outputRange(-ElevatorConstants.Elevator.maxElevatorSpeed, ElevatorConstants.Elevator.maxElevatorSpeed);

            elevatorRightConfig.closedLoop.maxMotion
                .allowedClosedLoopError(25)
                .maxAcceleration(60)
                .maxVelocity(60);
            
        }
    }

    public static class IntakeConfig {
        public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();

        static {
            intakeConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(ElevatorConstants.Intake.kStallLimit);
        }
    }

    public static class PivotConfig {
        public static final SparkMaxConfig pivotConfig = new SparkMaxConfig();

        static {
            // INFO: PIVOT
            pivotConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(ElevatorConstants.Pivot.kStallLimit);

            pivotConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .pid(
                    ElevatorConstants.Pivot.kP, 
                    ElevatorConstants.Pivot.kI, 
                    ElevatorConstants.Pivot.kD);
                    
            pivotConfig.closedLoop.maxMotion
                .maxAcceleration(ElevatorConstants.Pivot.kMaxAcceleration)
                .maxVelocity(ElevatorConstants.Pivot.kMaxVelocity)
                .allowedClosedLoopError(ElevatorConstants.Pivot.kAllowedClosedLoopError);
            
            
        }
        
    }
    
}
