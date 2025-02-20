// INFO: ROBOT IMPORTS
package frc.robot;
// INFO: WPILIB IMPORTS
import frc.robot.Constants.SwerveConstants.ModuleConstants;
// INFO: REV IMPORTS
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
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
            double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps;
            
            drivingConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(50);
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
        public static final SparkMaxConfig elevatorLeft = new SparkMaxConfig();  
        static {
            elevatorLeft
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(40)
            // TODO SEE BELOW:
            // Will need to be set for each motor
            // One will need to be inverted, the other not
            .inverted(false);
            elevatorLeft.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // TODO: TUNE? FIGURE OUT WHAT TO DO WITH IT
            .pid(0, 0, 0)
            // TODO: FIGURE OUT FF. DO WE NEED IT? HOW DO WE USE IT?
            // .velocityFF(0)
            .outputRange(-1, 1);
        }
        public static final SparkMaxConfig rightMotor = new SparkMaxConfig();  
        static {
            rightMotor
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(40)
            // TODO SEE BELOW:
            // Will need to be set for each motor
            // One will need to be inverted, the other not
            .inverted(true);
            rightMotor.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // TODO: TUNE? FIGURE OUT WHAT TO DO WITH IT
            .pid(0, 0, 0)
            // TODO: FIGURE OUT FF. DO WE NEED IT? HOW DO WE USE IT?
            // .velocityFF(0)
            .outputRange(-1, 1);
        }
        
    }
}
