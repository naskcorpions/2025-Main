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
    private boolean canRunIntake = false;
    @Override
    public void periodic() {
        // ...existing periodic tasks (if any)...
        // Print joint encoder value if joint motor is stopped.
        if (!limitSwitch.get()) {
        canRunIntake = false;
            
        }else {canRunIntake = true;}
    }
    
    //////////////////////////////////////////////////////////////////////////////
    // Intake Motor Control Methods - meets requirements:
    // Spin if switch is not activated; stop if switch is pressed;
    // Reverse always ignores the switch.
    //////////////////////////////////////////////////////////////////////////////
    public void runIntake() {
        // Invert logic: if the switch is pressed (true), stop the motor.
        if (limitSwitch.get()) {
            intakeMotor.set(0);
        } else {
            intakeMotor.set(ElevatorConstants.Intake.intakeSpeed);
        }
    }
    
    public void stopIntake() {
        intakeMotor.set(0);
    }
    
    // Use the separate adjustable speed for reverse intake.
    public void reverseIntake() {
        intakeMotor.set(-ElevatorConstants.Intake.reverseIntakeSpeed);
    }
}
