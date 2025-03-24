// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj.Tracer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.BooleanSupplier;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.revrobotics.spark.SparkClosedLoopController;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;


public class PivotSubsystem extends SubsystemBase{
    public static PivotSubsystem instance;
    public static PivotSubsystem getInstance() {
        if (instance == null) {
            instance = new PivotSubsystem();
        }
        return instance;
    }
    private static SparkMax pivotMotor = new SparkMax(ElevatorConstants.Pivot.kPivotMotorID, MotorType.kBrushless);
    SparkClosedLoopController pivotClosedLoopController;

    private static boolean runPivotMotor = true;
    private static double wantedPosition = 0.24;
    private static String wantedPositionName;

    private static double encoderPosition;

    
    public PivotSubsystem() {
        pivotClosedLoopController = pivotMotor.getClosedLoopController();
        pivotMotor.configure(Configs.PivotConfig.pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        if (runPivotMotor) {
            pivotClosedLoopController.setReference(wantedPosition, ControlType.kMAXMotionPositionControl);
            // pivotMotor.set(wantedPosition);
        } else {
            pivotMotor.stopMotor();
        }

        encoderPosition = pivotMotor.getAbsoluteEncoder().getPosition();
    }

    public static double getEncoderValue() {
        return encoderPosition;
    }


    public static void stopMotor() { runPivotMotor = false; 
        wantedPositionName = "STOP"; }
    public static void setIntakePosition() { wantedPosition = ElevatorConstants.Pivot.kIntakePos; runPivotMotor = true;
        wantedPositionName = "Intake"; }
    public static void setOuttakePosition() { wantedPosition = ElevatorConstants.Pivot.kOuttakePose; runPivotMotor = true;
        wantedPositionName = "Outtake"; }
    // public static void setL4Position() { wantedPosition = ElevatorConstants.Pivot.kL4Pose; runPivotMotor = true; 
    //     wantedPositionName = "L4"; }

    public static String getPivotPoseName() {
        return wantedPositionName;
    }

    public Command pivotOuttake() {
        return new InstantCommand( () -> {
            wantedPositionName = "Outtake";
            wantedPosition = ElevatorConstants.Pivot.kOuttakePose;
            runPivotMotor = true;
            pivotOuttake();
            System.out.println("PIVOT AUTI");
        });
    }




    
}
