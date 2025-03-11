// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.Set;

// import com.revrobotics.RelativeEncoder;

public class ElevatorSubsystem extends SubsystemBase {

    // ELEVATOR MOTORS
        private static SparkMax elevatorLeft = new SparkMax(ElevatorConstants.Elevator.kElevatorLeftMotor, MotorType.kBrushless);
        private static SparkMax elevatorRight = new SparkMax(ElevatorConstants.Elevator.kElevatorRightMotor, MotorType.kBrushless);
        private static SparkClosedLoopController closedLoopController = elevatorRight.getClosedLoopController();
        private static RelativeEncoder rightEncoder;  // new encoder for left motor
        
        // LIMIT SWITCHES
        private static DigitalInput upperSwitch = new DigitalInput(SensorPorts.LimitSwitches.elevatorUpperLimit);
        private static DigitalInput lowerSwitch = new DigitalInput(SensorPorts.LimitSwitches.elevatorLowerLimit);
        public static boolean getUpperLimitSwitch() { return !upperSwitch.get(); }
        public static boolean getLowerLimitSwitch() { return !lowerSwitch.get(); }
        
        // OTHER
        private static double wantedPosition;
        SendableChooser<Boolean> positionControlChooser = new SendableChooser<Boolean>();
    
        
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
            rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorBottomPose);  // reset encoder value to 0 on startup

            positionControlChooser.setDefaultOption("True", true);
            positionControlChooser.addOption("False", false);
            SmartDashboard.putData("Position Control Mode", positionControlChooser);

        }
        
        

        @Override
        public void periodic() {
            // if (getLowerLimitSwitch()) { stopElevator(); }

            // REVIEW: Set Poses to 0 the elevator (see above note)
            // if (getLowerLimitSwitch()) { rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorBottomPose); /* Bottom Pose */ }
            // if (upperSwitch.get()) { rightEncoder.setPosition(ElevatorConstants.Elevator.kElevatorTopPose); /* Top Pose */ }
            
            if (positionControlChooser.getSelected()) {
                // closedLoopController.setReference(wantedPosition, ControlType.kMAXMotionVelocityControl);  
            } else {
                // closedLoopController.
            }
            SmartDashboard.putNumber("Elevator Wanted Pose", wantedPosition);
        }
        public static void setElevatorPoseBottom() {wantedPosition = ElevatorConstants.Elevator.kElevatorBottomPose; SmartDashboard.putString("Elevator Pose", "Bottom"); }
        public static void setElevatorPoseL3() {wantedPosition = ElevatorConstants.Elevator.kElevatorL3CoralPose; SmartDashboard.putString("Elevator Pose", "L3"); }

    
        // New method to run both elevator motors at a given speed
        public static void runElevator(double speed) {
            elevatorLeft.set(speed);
            elevatorRight.set(speed);
        }
    
        // New method to stop both elevator motors
        public static void stopElevator() {
            elevatorLeft.stopMotor();
            elevatorRight.stopMotor();
        }
    
        // New getter for the encoder value in degrees
        public static double getEncoderValue() {
            return rightEncoder.getPosition();
        }


}