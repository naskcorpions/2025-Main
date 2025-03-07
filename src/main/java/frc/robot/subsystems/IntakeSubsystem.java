// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;

// INFO: REV IMPORTS
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

public class IntakeSubsystem extends SubsystemBase {
    private static DigitalInput limitSwitch = new DigitalInput(SensorPorts.LimitSwitches.intakeSwitch);
    public static boolean getIntakeSwitch() { return !limitSwitch.get(); }

    private SparkMax intakeMotor = new SparkMax(ElevatorConstants.Intake.kIntakeMotor, MotorType.kBrushless);
    private boolean canRunIntake;

    public IntakeSubsystem() {
        intakeMotor.configure(Configs.IntakeConfig.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        // Limit Switch Checker
        if (!limitSwitch.get()) {
            canRunIntake = false;  
        } else {canRunIntake = true;}

    }
    
    public void runIntake() {
        if (canRunIntake) {
            intakeMotor.set(ElevatorConstants.Intake.intakeSpeed);
        } else {
            stopIntake();
        }
    }
    
    public void reverseIntake() {
        intakeMotor.set(-ElevatorConstants.Intake.reverseIntakeSpeed);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }
}
