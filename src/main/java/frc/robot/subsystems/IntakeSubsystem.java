// INFO: ROBOT IMPORTS
package frc.robot.subsystems;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;

// INFO: REV IMPORTS
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

public class IntakeSubsystem extends SubsystemBase {
    //////////////////////////////////////////////////////////////////////////////
    // Fields: Intake Motor & Sensor
    //////////////////////////////////////////////////////////////////////////////
    private DigitalInput limitSwitch = new DigitalInput(SensorPorts.LimitSwitches.intakeSwitch);
    private SparkMax intakeMotor = new SparkMax(ElevatorConstants.Intake.kIntakeMotor, MotorType.kBrushless);
    private boolean spinning = false;

    @Override
    public void periodic() {
        // ...existing periodic tasks (if any)...
        // Print joint encoder value if joint motor is stopped.
    }
    
    //////////////////////////////////////////////////////////////////////////////
    // Intake Motor Control Methods (only act on command)
    //////////////////////////////////////////////////////////////////////////////
    public void autoSpin() {
        // This method will not be called automatically.
        if (!limitSwitch.get()) {
            intakeMotor.set(ElevatorConstants.Intake.intakeSpeed);
        } else {
            intakeMotor.set(0);
        }
    }
    
    // Stop the intake motor.
    public void stopIntake() {
        intakeMotor.set(0);
    }
    
    // Run the intake motor in reverse.
    public void reverseIntake() {
        intakeMotor.set(-ElevatorConstants.Intake.intakeSpeed);
    }

    public void stopMotor() {
        intakeMotor.set(0);
    }
    
    public void toggleSpin() {
        if (spinning) {
            stopMotor();
            spinning = false;
        } else {
            if (!limitSwitch.get()) { // Only spin if limit switch is not triggered.
                intakeMotor.set(ElevatorConstants.Intake.intakeSpeed);
                spinning = true;
            }
        }
    }
    
    public void outputSpin() {
        intakeMotor.set(-ElevatorConstants.Intake.intakeSpeed);
    }
}
