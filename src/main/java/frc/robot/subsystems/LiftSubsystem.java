// INFO: ROBOT IMpORTS
package frc.robot.subsystems;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.SensorPorts;
import edu.wpi.first.wpilibj.DigitalInput;
// INFO: WPILIB IMPORTS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// INFO: REV IMPORTS
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
//INFO: PHOENIX IMPORTS
import com.ctre.phoenix6.hardware.TalonFX;

public class LiftSubsystem extends SubsystemBase {
    TalonFX liftLeft = new TalonFX(14);
    TalonFX liftRight = new TalonFX(13);

    public LiftSubsystem() {

    }

    public void runLift(double speed) {
        liftLeft.set(Math.sqrt(speed * speed));
        liftRight.set(-Math.sqrt(speed * speed));
    }

    public void stopLift() {
        liftLeft.set(0);
        liftRight.set(0);
    }
    
    
}
