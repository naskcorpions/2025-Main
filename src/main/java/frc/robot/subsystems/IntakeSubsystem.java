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

    //////////////////////////////////////////////////////////////////////////////
    // Fields: Joint Motor & Encoder, Constants from ElevatorConstants.Intake
    //////////////////////////////////////////////////////////////////////////////
    private SparkMax jointMotor = new SparkMax(ElevatorConstants.Intake.jointMotor, MotorType.kBrushless);
    private final RelativeEncoder jointEncoder = jointMotor.getEncoder();
    private final double jointUpperLimit = ElevatorConstants.Intake.jointUpperLimit;
    private final double jointLowerLimit = ElevatorConstants.Intake.jointLowerLimit;
    private final double jointSpeed = ElevatorConstants.Intake.jointSpeed;
    
    @Override
    public void periodic() {
        // ...existing periodic tasks (if any)...
        // Print joint encoder value if joint motor is stopped.
        if(jointMotor.get() == 0) {
            System.out.println("Joint Encoder Value: " + jointEncoder.getPosition());
        }
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
    
    //////////////////////////////////////////////////////////////////////////////
    // Joint Motor Control Methods (with Encoder Limits)
    //////////////////////////////////////////////////////////////////////////////
    public void moveJointUp() {
        if(jointEncoder.getPosition() < jointUpperLimit) {
            jointMotor.set(jointSpeed);
        } else {
            jointMotor.set(0);
        }
    }
    
    public void moveJointDown() {
        if(jointEncoder.getPosition() > jointLowerLimit) {
            jointMotor.set(-jointSpeed);
        } else {
            jointMotor.set(0);
        }
    }
    
    public void stopJointMotor() {
        jointMotor.set(0);
    }
}
