// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkClosedLoopController;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;


public class PivotSubsystem extends SubsystemBase{
    private static SparkMax pivotMotor = new SparkMax(ElevatorConstants.Pivot.kPivotMotorID, MotorType.kBrushless);
    SparkClosedLoopController pivotClosedLoopController;

    private static boolean runPivotMotor = false;
    private static double wantedPosition = 0;

    private static double encoderPosition;
    
    public PivotSubsystem() {
        pivotClosedLoopController = pivotMotor.getClosedLoopController();
        pivotMotor.configure(Configs.ElevatorConfig.pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        if (runPivotMotor) {
            // pivotClosedLoopController.setReference(wantedPosition, ControlType.kMAXMotionPositionControl);
            pivotMotor.set(wantedPosition);
        } else {
            pivotMotor.set(0);
        }

        encoderPosition = pivotMotor.getAbsoluteEncoder().getPosition();
    }

    public static double getEncoderValue() {
        return encoderPosition;
    }

    public static void setDrivePosition() { wantedPosition = ElevatorConstants.Pivot.kDrivePose; runPivotMotor = true;
        System.out.println("DRIVEPOSE"); }

    public static void setIntakePosition() { wantedPosition = ElevatorConstants.Pivot.kIntakePose; runPivotMotor = true;
    System.out.println("INTAKEPOSE"); }

    public static void setOuttakePosition() { wantedPosition = ElevatorConstants.Pivot.kOuttakePose; runPivotMotor = true;
    System.out.println("OUTTAKEPOSE"); }

    public static void stopMotor() { runPivotMotor = false; 
        System.out.println("PIVOTSTOP"); }
    




    
}
