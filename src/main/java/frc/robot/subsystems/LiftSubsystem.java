package frc.robot.subsystems;
//
import frc.robot.Configs;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.LiftConstants;
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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class LiftSubsystem extends SubsystemBase {
    // private  liftLeft = new SparkMax(LiftConstants.Lift.kLiftLeftMotor, MotorType.kBrushless);
    // private TalonFX liftRight = new TalonFX(LiftConstants.Lift.kLiftRightMotor, MotorType.kBrushless);
    WPI_Talonfx liftLeft = new WPI_Talonfx(LiftConstants.Lift.kLiftLeftMotor);

    public LiftSubsystem() {
        liftLeft.configure(Configs.ElevatorConfig.liftLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        liftRight.configure(Configs.ElevatorConfig.liftRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void runLift(double speed) {
        // if(getEncoderValue() >= 200) { // added check before running the elevator
        //     stopElevator();
        //     return;
        // }
        liftLeft.set(speed);
        liftRight.set(speed);
    }

    public void stopLift() {
        liftLeft.set(0);
        liftRight.set(0);
    }
    
    
}
