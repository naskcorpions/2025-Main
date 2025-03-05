// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.Set;

// import com.revrobotics.RelativeEncoder;

public class ElevatorSubsystem extends SubsystemBase {

    // Create elevator Motors
    private static SparkMax elevatorLeft = new SparkMax(ElevatorConstants.kElevatorLeftMotor, MotorType.kBrushless);
    private static SparkMax elevatorRight = new SparkMax(ElevatorConstants.kElevatorRightMotor, MotorType.kBrushless);
    // private RelativeEncoder rightEncoder;  // new encoder for left motor

    private static double elevatorSpeed = 0.3;


    // Proper constructor initializing motors and encoders
    public ElevatorSubsystem() {
        elevatorLeft.configure(Configs.ElevatorConfig.elevatorLeft, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorRight.configure(Configs.ElevatorConfig.rightMotor, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        // rightEncoder = elevatorRight.getEncoder();   // initialize left encoder
        // rightEncoder.setPosition(0);  // reset encoder value to 0 on startup
    }

    // New method to run both elevator motors at a given speed
    public void runElevator(double speed) {
        // if(getEncoderValue() >= 200) { // added check before running the elevator
        //     stopElevator();
        //     return;
        // }
        elevatorLeft.set(speed);
        elevatorRight.set(speed);
    }

    // New method to stop both elevator motors
    public static void stopElevator() {
        elevatorLeft.set(0);
        elevatorRight.set(0);
    }

    // New getter for the encoder value in degrees
    // public double getEncoderValue() {
    //     return rightEncoder.getPosition() * 360.0;
    // }

    @Override
    public void periodic() {
        // System.out.println("Elevator Encoder: " + getEncoderValue());
        // if(getEncoderValue() > 200) { // updated threshold from 1000 to 200
        //     stopElevator();
        //     System.out.println("Maximum encoder value reached; elevator stopped.");
        // }
    }

    public static void runTestUp() {
        elevatorLeft.set(elevatorSpeed);
        elevatorRight.set(elevatorSpeed);
    }
    public static void runTestDown() {
        elevatorLeft.set(-elevatorSpeed);
        elevatorRight.set(-elevatorSpeed);
    }
    

}