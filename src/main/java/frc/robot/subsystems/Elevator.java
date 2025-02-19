package frc.robot.subsystems;

import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder; // added import

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

    // Create elevator Motors
    private SparkMax elevatorLeft = new SparkMax(ElevatorConstants.kElevatorLeftMotor, MotorType.kBrushless);
    private SparkMax elevatorRight = new SparkMax(ElevatorConstants.kElevatorRightMotor, MotorType.kBrushless);
    private RelativeEncoder rightEncoder;  // new encoder for left motor

    // Proper constructor initializing motors and encoders
    public Elevator() {
        elevatorLeft.configure(Configs.ElevatorConfig.elevatorLeft, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorRight.configure(Configs.ElevatorConfig.rightMotor, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        rightEncoder = elevatorRight.getEncoder();   // initialize left encoder
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

    // New getter for the left encoder value
    public double getEncoderValue() {
        return rightEncoder.getPosition();
    }

    @Override
    public void periodic() {
        System.out.println("Elevator Encoder: " + getEncoderValue());
    }

}