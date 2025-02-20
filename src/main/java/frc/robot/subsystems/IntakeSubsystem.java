package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    // Digital switch on channel 8; adjust wiring as needed.
    private DigitalInput limitSwitch = new DigitalInput(8);
    // Intake motor on port 11 (example) and using a brushless motor type
    private SparkMax intakeMotor = new SparkMax(12, MotorType.kBrushless);

    // New method that spins the motor while button is pressed,
    // but stops it if the limit switch is activated.
    public void manualSpin(double speed) {
        if (!limitSwitch.get()) { // switch NOT pressed
            intakeMotor.set(speed);
        } else {
            intakeMotor.set(0);
        }
    }

    // New method to stop the motor completely.
    public void stopMotor() {
        intakeMotor.set(0);
    }
    
    // Optionally remove or repurpose the periodic() override
    @Override
    public void periodic() {
        // ...existing periodic tasks (if any)...
    }
}
