// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;
import edu.wpi.first.wpilibj.DigitalInput;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.Set;

// import com.revrobotics.RelativeEncoder;

public class ElevatorSubsystem extends SubsystemBase {

    // ELEVATOR MOTORS
    private SparkMax elevatorLeft = new SparkMax(ElevatorConstants.Elevator.kElevatorLeftMotor, MotorType.kBrushless);
    private SparkMax elevatorRight = new SparkMax(ElevatorConstants.Elevator.kElevatorRightMotor, MotorType.kBrushless);
    private static RelativeEncoder rightEncoder;  // new encoder for left motor

    // LIMIT SWITCHES
    private static DigitalInput upperSwitch = new DigitalInput(SensorPorts.LimitSwitches.elevatorUpperLimit);
    private static DigitalInput lowerSwitch = new DigitalInput(SensorPorts.LimitSwitches.elevatorLowerLimit);
    public static boolean getUpperLimitSwitch() { return upperSwitch.get(); }
    public static boolean getLowerLimitSwitch() { return lowerSwitch.get(); }

    
        /* IMPORTANT:
         * NOTE:
         * NOTE:    Ensure that the .setPosition(double) on encoder.setPosition(0) is the right method
         * NOTE:    It should set the encoder value based on where it is
         * NOTE:        Example: If the elevator hits the lower limit switch, it should
         * NOTE:                    tell the elevator to stop, and then set the encoder value
         * NOTE:                    to the bottom position (basically resetting the encoder)
         * NOTE:    USE CASE:
         * NOTE:        When we start the code/enable, if it starts with the elevator above the minimum
         * NOTE:        elevation, then we can run the elevator down to 0 it's position
         * NOTE:      
         */
        // IMPORTANT:
        
        public ElevatorSubsystem() {
            elevatorLeft.configure(Configs.ElevatorConfig.elevatorLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            elevatorRight.configure(Configs.ElevatorConfig.elevatorRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            
            rightEncoder = elevatorRight.getEncoder();   // initialize left encoder
            // REVIEW: 0 elevator on start (see above note)
            rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorBottonPose);  // reset encoder value to 0 on startup
        }
    
        @Override
        public void periodic() {
            // If Limit Switch Hit, Stop Elevator
            if (lowerSwitch.get() || upperSwitch.get()) { stopElevator(); }
            // REVIEW: Set Poses to 0 the elevator (see above note)
            if (lowerSwitch.get()) { rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorBottonPose); /* Bottom Pose */ }
            if (upperSwitch.get()) { rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorBottonPose); /* Top Pose */ }
    
        }
    
        // New method to run both elevator motors at a given speed
        public void runElevator(double speed) {
            elevatorLeft.set(speed);
            elevatorRight.set(speed);
        }
    
        // New method to stop both elevator motors
        public void stopElevator() {
            elevatorLeft.set(0);
            elevatorRight.set(0);
        }
    
        // New getter for the encoder value in degrees
        public static double getEncoderValue() {
            return rightEncoder.getPosition();
        }

}