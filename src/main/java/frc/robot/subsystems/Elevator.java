package frc.robot.subsystems;

import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

    // Create elevator Motors
    private SparkMax elevatorLeft = new SparkMax(ElevatorConstants.kElevatorLeftMotor, MotorType.kBrushless);
    private SparkMax elevatorRight = new SparkMax(ElevatorConstants.kElevatorRightMotor, MotorType.kBrushless);

    //TODO: One motor for the elevator will need to be inverted. Should we use two seperate configs for inverted, and not, or just invert using a negative in here?
    /** Initializes Elevator. Should be called in RobotContainer once to initialize */
    public void Elevator() {
        elevatorLeft.configure(Configs.ElevatorConfig.elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorRight.configure(Configs.ElevatorConfig.elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    @Override
    public void periodic() {

    }

}
