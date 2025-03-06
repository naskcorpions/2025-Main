// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;
import frc.robot.Constants.ElevatorConstants.Elevator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import edu.wpi.first.wpilibj.DigitalInput;
// INFO: REV IMPROTS
import com.revrobotics.spark.SparkMax;

import java.util.function.BooleanSupplier;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;


public class ElevatorSubsystem extends SubsystemBase{
    // Create Motors and Related Things
    private static SparkMax elevatorRightMotor = new SparkMax(ElevatorConstants.Elevator.kElevatorRightMotor, MotorType.kBrushless);
    private static SparkMax elevatorLeftMotor = new SparkMax(ElevatorConstants.Elevator.kElevatorLeftMotor, MotorType.kBrushless);
    private static SparkClosedLoopController elevatorClosedLoopController = elevatorRightMotor.getClosedLoopController();

    private static DigitalInput topLimitSwitch = new DigitalInput(SensorPorts.LimitSwitches.elevatorUpperLimit);
    private static DigitalInput bottomLimitSwitch= new DigitalInput(SensorPorts.LimitSwitches.elevatorLowerLimit);

    private static boolean canRun;
    private static double wantedPosition = 0;

    // Variables
    private static double encoderValue;


    public ElevatorSubsystem() {
        elevatorRightMotor.configure(Configs.ElevatorConfig.elevatorRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorLeftMotor.configure(Configs.ElevatorConfig.elevatorLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


    }

    @Override
    public void periodic() {
        encoderValue = elevatorRightMotor.getEncoder().getPosition();
        if (!topLimitSwitch.get() || !bottomLimitSwitch.get()) {
            elevatorClosedLoopController.setReference(wantedPosition, ControlType.kMAXMotionPositionControl);
        } else {
            stopElevator();
        }

        if (topLimitSwitch.get()) { resetEncoderTop(); }
        if (bottomLimitSwitch.get()) { resetEncoderBottom();}

    }

    public static double getEncoderValue() {
        return encoderValue;
    }
    public static void resetEncoderBottom() {
        elevatorRightMotor.getEncoder().setPosition(ElevatorConstants.Elevator.kElevatorBottomPose);
    }
    public static void resetEncoderTop() {
        elevatorRightMotor.getEncoder().setPosition(ElevatorConstants.Elevator.kElevatorTopPose);
    }
    private static void stopElevator() {
        elevatorRightMotor.set(0);
        elevatorLeftMotor.set(0);
    }

    public static void setElevatorBottom() { wantedPosition = ElevatorConstants.Elevator.kElevatorBottomPose; }
    public static void setElevatorL1() { wantedPosition = ElevatorConstants.Elevator.kElevatorL1CoralPose; }
    public static void setElevatorL2() { wantedPosition = ElevatorConstants.Elevator.kElevatorL2CoralPose; }
    public static void setElevatorL3() { wantedPosition = ElevatorConstants.Elevator.kElevatorL3CoralPose; }
    public static void setElevatorL4() { wantedPosition = ElevatorConstants.Elevator.kElevatorL4CoralPose; }
    public static void setElevatorTop() { wantedPosition = ElevatorConstants.Elevator.kElevatorTopPose; }



}
