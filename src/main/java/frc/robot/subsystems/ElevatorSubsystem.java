package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ElevatorSubsystem extends SubsystemBase{
    private static SparkMax elevatorRightMotor = new SparkMax(Constants.ElevatorConstants.rightMotorID, MotorType.kBrushless);
    private static SparkMax elevatorLeftMotor = new SparkMax(Constants.ElevatorConstants.leftMotorID, MotorType.kBrushless);    

    private static DigitalInput lowerLimitSwitch = new DigitalInput(0);
    private static DigitalInput upperLimitSwitch = new DigitalInput(2);
    
    private boolean stopElevator = false;

    public void ElevatorSubsystem() {
        elevatorRightMotor.configure(Configs.ElevatorConfig.elevatorRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorLeftMotor.configure(Configs.ElevatorConfig.elevatorLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    @Override
    public void periodic() {
        if (lowerLimitSwitch.get()) { stopElevator(); stopElevator = true; /* SET ELEVATOR ENCODER TO MIN POS */ }
            else { stopElevator = false; }
        if (upperLimitSwitch.get()) { stopElevator(); stopElevator = true; /* SET ELEVATOR ENCODER TO MAX POS */ }
            else { stopElevator = false; }
        
        if (!stopElevator) {
            // Set Elevator Position
        }
    }

    public static void stopElevator() {
        elevatorRightMotor.set(0);
        elevatorLeftMotor.set(0);
    }

    private Command moveToSetpoint() {
        return new RunCommand(
            () -> {

            }, null);
    }
}
