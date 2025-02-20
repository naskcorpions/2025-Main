package frc.robot.subsystems;

import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder; // added import

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Elevator now extends the new ElevatorSubsystems to reuse its logic.
public class Elevator extends ElevatorSubsystems {
    public Elevator() {
        super();
    }
}